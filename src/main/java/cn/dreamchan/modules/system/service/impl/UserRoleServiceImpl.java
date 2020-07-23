package cn.dreamchan.modules.system.service.impl;

import cn.dreamchan.modules.system.mapper.UserRoleMapper;
import cn.dreamchan.modules.system.pojo.entity.UserRoleEntity;
import cn.dreamchan.modules.system.service.UserRoleService;
import cn.dreamchan.common.base.BaseService;

import org.springframework.stereotype.Service;

/**
 * 用户和角色关联 服务实现类
 *
 * @author DreamChan
 * @since 2020-07-15
 */
@Service
public class UserRoleServiceImpl extends BaseService<UserRoleMapper, UserRoleEntity> implements UserRoleService {

}

