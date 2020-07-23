package cn.dreamchan.modules.monitor.service.mapstruct;

import cn.dreamchan.component.security.pojo.LoginUserDetails;
import cn.dreamchan.modules.monitor.pojo.vo.OnlineUserVo;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author DreamChan
 */
@Component
@Mapper(componentModel = "spring")
public interface OnlineUserMapStruct {

    OnlineUserVo toVo(LoginUserDetails source);

}
