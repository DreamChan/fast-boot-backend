package cn.dreamchan.common.base;


import cn.dreamchan.common.utils.SecurityUtils;
import cn.dreamchan.common.utils.StringUtils;
import cn.dreamchan.component.security.pojo.LoginUserDetails;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * Controller 基类
 *
 * @author DreamChan
 */
public class BaseController<Q extends BasePageQueryParam, E extends BaseEntity> {

    /**
     * 获取当前 登录用户
     */
    public LoginUserDetails getLoginUser() {
        return SecurityUtils.getLoginUser();
    }

    /**
     * 封装查询参数
     */
    public QueryWrapper getQueryWrapper(BaseMapStruct baseMapStruct, Q param) {
        QueryWrapper<E> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity((E) baseMapStruct.pageQueryParamToEntity(param));

        String sortColumn = param.getSortColumn();
        String underSortColumn = StringUtils.lowerCamelToLowerUnderscore(sortColumn);
        if (param.getIsAsc()) {
            queryWrapper.orderByAsc(underSortColumn);
        } else {
            queryWrapper.orderByDesc(underSortColumn);
        }
        return queryWrapper;
    }

}
