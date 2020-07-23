package cn.dreamchan.modules.system.service.impl;

import cn.dreamchan.modules.system.mapper.RoleMenuMapper;
import cn.dreamchan.modules.system.pojo.entity.MenuEntity;
import cn.dreamchan.modules.system.pojo.entity.RoleMenuEntity;
import cn.dreamchan.modules.system.service.RoleMenuService;
import cn.dreamchan.common.base.BaseService;

import org.springframework.stereotype.Service;

/**
 * 角色和菜单关联 服务实现类
 *
 * @author DreamChan
 * @since 2020-07-15
 */
@Service
public class RoleMenuServiceImpl extends BaseService<RoleMenuMapper, RoleMenuEntity> implements RoleMenuService {

    @Override
    public boolean checkMenuExistRole(Long menuId) {
        int count = this.lambdaQuery().eq(RoleMenuEntity::getMenuId, menuId).count();
        return count > 0 ? true : false;
    }
}

