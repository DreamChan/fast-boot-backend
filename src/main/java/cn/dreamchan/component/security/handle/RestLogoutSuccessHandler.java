package cn.dreamchan.component.security.handle;

import cn.dreamchan.common.biz.R;
import cn.dreamchan.common.constant.Constants;
import cn.dreamchan.common.utils.JsonUtil;
import cn.dreamchan.common.utils.ServletUtils;
import cn.dreamchan.common.utils.StringUtils;
import cn.dreamchan.component.redis.RedisService;
import cn.dreamchan.component.security.pojo.LoginUserDetails;
import cn.dreamchan.modules.login.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户退出处理类
 *
 * @author DreamChan
 */
@Configuration
public class RestLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private RedisService redisService;

    /**
     * 退出处理
     *
     * 自定义JwtAuthenticationTokenFilter 需要配置在 LogoutFilter之前， 否则 authentication 获取为null
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        LoginUserDetails loginUser = (LoginUserDetails) authentication.getPrincipal();

        if (StringUtils.isNotNull(loginUser)) {
            // 删除用户缓存记录
            String userKey = Constants.LOGIN_TOKEN_KEY + loginUser.getToken();
            redisService.del(userKey);
        }

        ServletUtils.renderString(response,
                HttpStatus.OK.value(),
                JsonUtil.toJson(R.success("退出成功")));

    }
}
