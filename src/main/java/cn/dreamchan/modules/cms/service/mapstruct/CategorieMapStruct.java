package cn.dreamchan.modules.cms.service.mapstruct;

import cn.dreamchan.modules.cms.pojo.entity.*;
import cn.dreamchan.modules.cms.pojo.vo.*;
import cn.dreamchan.modules.cms.pojo.dto.*;
import org.mapstruct.Mapper;
import cn.dreamchan.common.base.BaseMapStruct;
import org.springframework.stereotype.Component;



@SuppressWarnings("all")
@Component
@Mapper(componentModel = "spring")
public interface CategorieMapStruct extends BaseMapStruct<CategorieVo, CategorieEntity, CategorieEditParam, CategoriePageQueryParam >{

}
