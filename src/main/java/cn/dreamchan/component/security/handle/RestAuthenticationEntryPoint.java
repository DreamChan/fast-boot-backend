package cn.dreamchan.component.security.handle;

import cn.dreamchan.common.biz.R;
import cn.dreamchan.common.biz.ResCodeEnum;
import cn.dreamchan.common.utils.JsonUtil;
import cn.dreamchan.common.utils.ServletUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 认证失败处理
 *
 * @author DreamChan
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {

        ServletUtils.renderString(response,
                HttpStatus.UNAUTHORIZED.value(),
                JsonUtil.toJson(R.failure(ResCodeEnum.UNAUTHORIZED, "认证失败，无法访问系统资源")));

    }


}
