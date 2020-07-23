package cn.dreamchan.component.handler;

import cn.dreamchan.common.utils.SecurityUtils;
import cn.dreamchan.component.security.pojo.LoginUserDetails;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * mybatis plus 字段自动填充
 *
 * @author DreamChan
 */
@Slf4j
@Component
public class MyBatisMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.debug("start insert fill ....");

        Authentication authentication = SecurityUtils.getAuthentication();
        if(authentication != null){
            LoginUserDetails loginUserDetails = SecurityUtils.getLoginUser();
            this.setFieldValByName("createBy",  loginUserDetails.getUsername(), metaObject);
            this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
            this.setFieldValByName("updateBy",  loginUserDetails.getUsername(), metaObject);
            this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug("start update fill ....");

        Authentication authentication = SecurityUtils.getAuthentication();
        if(authentication != null){
            LoginUserDetails loginUserDetails = SecurityUtils.getLoginUser();
            this.setFieldValByName("updateBy",  loginUserDetails.getUsername(), metaObject);
            this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }
    }


}
