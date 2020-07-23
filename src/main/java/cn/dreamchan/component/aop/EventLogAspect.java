package cn.dreamchan.component.aop;

import cn.dreamchan.common.utils.*;
import cn.dreamchan.component.aop.annotation.EventLog;
import cn.dreamchan.component.event.OperLogEvent;
import cn.dreamchan.component.security.pojo.LoginUserDetails;
import cn.dreamchan.modules.monitor.pojo.entity.OperLogEntity;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * 操作日志处理
 *
 * @author DreamChan
 */
@Order(10)
@Aspect
@Component
@Slf4j
public class EventLogAspect {
    @Autowired
    private ApplicationContext applicationContext;

    @SneakyThrows
    @Around("@annotation(eventLog)")
    public Object around(ProceedingJoinPoint point, EventLog eventLog) {
        String strClassName = point.getTarget().getClass().getName();
        String strMethodName = point.getSignature().getName();
        log.debug("EventLogAspect 类名:<{}>, 方法:<{}>", strClassName, strMethodName);

        OperLogEntity operLogEntity = buildOperLog();

        Object[] bodyArgs = point.getArgs();

        operLogEntity.setMessage(eventLog.message());
        operLogEntity.setBusinessType(eventLog.businessType().getCode());
        long startTime = Instant.now().toEpochMilli();

        Object result = point.proceed();

        long endTime = Instant.now().toEpochMilli();
        operLogEntity.setExecuteTime(endTime - startTime);

        applicationContext.publishEvent(new OperLogEvent(this, operLogEntity));
        return result;
    }


    @SneakyThrows
    private OperLogEntity buildOperLog() {
        HttpServletRequest request = ServletUtils.getRequest();
        OperLogEntity operLogEntity = new OperLogEntity();
        operLogEntity.setRequestDate(LocalDateTime.now());

        LoginUserDetails loginUser = SecurityUtils.getLoginUser();
        operLogEntity.setUserId(loginUser.getUserVo().getUserId());
        operLogEntity.setUserName(loginUser.getUsername());
        operLogEntity.setRequestUrl(request.getRequestURI());
        operLogEntity.setRequestMethod(request.getMethod());
        operLogEntity.setOperIp(IpUtils.getIpAddr(ServletUtils.getRequest()));

        String formParam = JsonUtil.toJson(request.getParameterMap());
        if (StringUtils.isNotBlank(formParam)) {
            // 获取 form 参数
            operLogEntity.setRequestParam(formParam);
        } else {
            operLogEntity.setRequestParam("请求参数为空");
        }
        return operLogEntity;
    }

}
