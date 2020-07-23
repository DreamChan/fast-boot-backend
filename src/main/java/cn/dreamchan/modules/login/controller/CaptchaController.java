package cn.dreamchan.modules.login.controller;

import cn.dreamchan.common.biz.R;
import cn.dreamchan.common.biz.ResObject;
import cn.dreamchan.common.constant.Constants;
import cn.dreamchan.component.redis.RedisService;
import com.wf.captcha.SpecCaptcha;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 获取验证码
 *
 * @author DreamChan
 */
@Api(tags = "验证码管理")
@RestController
public class CaptchaController {

    @Autowired
    private RedisService redisService;

    /**
     * 生成验证码
     */
    @GetMapping("/getCaptchaCode")
    public ResObject getCaptchaCode() {
        // 唯一标识
        String uuid = UUID.randomUUID().toString();
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;

        // 设置验证码图片宽、高
        SpecCaptcha specCaptcha = new SpecCaptcha(111, 36, 4);
        specCaptcha.setCharType(SpecCaptcha.TYPE_ONLY_NUMBER);
        String verifyCode = specCaptcha.text().toLowerCase();

        redisService.set(verifyKey, verifyCode, Constants.CAPTCHA_EXPIRATION);

        Map<String, Object> result = new HashMap<>();
        result.put("uuid", uuid);
        result.put("captchaImg", specCaptcha.toBase64());
        return R.success(result);
    }
}
