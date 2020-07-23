package cn.dreamchan.modules.login.service;

import cn.dreamchan.common.constant.Constants;
import cn.dreamchan.common.exception.CustomException;
import cn.dreamchan.common.utils.IpUtils;
import cn.dreamchan.common.utils.ServletUtils;
import cn.dreamchan.component.event.LoginLogEvent;
import cn.dreamchan.component.redis.RedisService;
import cn.dreamchan.modules.login.pojo.dto.LoginParam;
import cn.dreamchan.component.security.pojo.LoginUserDetails;
import cn.dreamchan.modules.monitor.pojo.entity.LoginLogEntity;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 登录校验方法
 *
 * @author DreamChan
 */
@Service
public class LoginService {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisService redisService;

    /**
     * 登录验证
     *
     * @return 结果
     */
    public String login(LoginParam loginParam) {
        String username = loginParam.getUsername();
        String password = loginParam.getPassword();
        String code = loginParam.getCode();
        String uuid = loginParam.getUuid();

        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisService.getStr(verifyKey);
        redisService.del(verifyKey);

        if (captcha == null) {
            throw new CustomException("验证码过期");
        }
        if (!code.equalsIgnoreCase(captcha)) {
            throw new CustomException("验证码错误");
        }
        // 用户验证
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
                authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                throw new CustomException("用户名或密码错误");
            } else {
                throw new CustomException("登录错误");
            }
        }

        LoginUserDetails loginUser = (LoginUserDetails) authentication.getPrincipal();

        LoginLogEntity loginLogEntity = new LoginLogEntity();
        loginLogEntity.setUserId(loginUser.getUserVo().getUserId());
        loginLogEntity.setUserName(username);
        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        loginLogEntity.setIpaddr(IpUtils.getIpAddr(ServletUtils.getRequest()));
        loginLogEntity.setOsName(userAgent.getOperatingSystem().getName());
        loginLogEntity.setBrowserName(userAgent.getBrowser().getName());
        loginLogEntity.setLoginTime(LocalDateTime.now());
        loginLogEntity.setStatus("0");
        loginLogEntity.setMessage("登录成功");
        applicationContext.publishEvent(new LoginLogEvent(this, loginLogEntity));

        // 生成token
        return tokenService.createToken(loginUser);
    }
}
