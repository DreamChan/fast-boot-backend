package cn.dreamchan.modules.system.service.impl;

import cn.dreamchan.modules.system.mapper.UserPostMapper;
import cn.dreamchan.modules.system.pojo.entity.UserPostEntity;
import cn.dreamchan.modules.system.service.UserPostService;
import cn.dreamchan.common.base.BaseService;

import org.springframework.stereotype.Service;

/**
 * 用户与岗位关联 服务实现类
 *
 * @author DreamChan
 * @since 2020-07-15
 */
@Service
public class UserPostServiceImpl extends BaseService<UserPostMapper, UserPostEntity> implements UserPostService {

}

