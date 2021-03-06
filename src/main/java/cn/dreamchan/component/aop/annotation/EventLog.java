package cn.dreamchan.component.aop.annotation;


import cn.dreamchan.common.enums.EventLogEnum;

import java.lang.annotation.*;

/**
 * 操作日志注解
 *
 * @author  DreamChan
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EventLog {
    /**
     * 日志记录信息
     */
    String message() default "";

    /**
     * 功能
     */
    EventLogEnum businessType() default EventLogEnum.QUERY;
}
