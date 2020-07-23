package cn.dreamchan.modules.monitor.service.mapstruct;

import cn.dreamchan.modules.monitor.pojo.entity.*;
import cn.dreamchan.modules.monitor.pojo.vo.*;
import cn.dreamchan.modules.monitor.pojo.dto.*;
import org.mapstruct.Mapper;
import cn.dreamchan.common.base.BaseMapStruct;
import org.springframework.stereotype.Component;



@SuppressWarnings("all")
@Component
@Mapper(componentModel = "spring")
public interface OperLogMapStruct extends BaseMapStruct<OperLogVo, OperLogEntity, OperLogEditParam, OperLogPageQueryParam >{

}
