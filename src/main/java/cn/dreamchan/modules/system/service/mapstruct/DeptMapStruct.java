package cn.dreamchan.modules.system.service.mapstruct;

import cn.dreamchan.modules.system.pojo.entity.*;
import cn.dreamchan.modules.system.pojo.vo.*;
import cn.dreamchan.modules.system.pojo.dto.*;
import org.mapstruct.Mapper;
import cn.dreamchan.common.base.BaseMapStruct;
import org.springframework.stereotype.Component;



@SuppressWarnings("all")
@Component
@Mapper(componentModel = "spring")
public interface DeptMapStruct extends BaseMapStruct<DeptVo, DeptEntity, DeptEditParam, DeptPageQueryParam >{

}
