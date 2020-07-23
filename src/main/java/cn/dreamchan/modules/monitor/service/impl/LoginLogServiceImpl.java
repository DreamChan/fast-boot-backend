package cn.dreamchan.modules.monitor.service.impl;

import cn.dreamchan.modules.monitor.mapper.LoginLogMapper;
import cn.dreamchan.modules.monitor.service.LoginLogService;
import cn.dreamchan.common.base.BaseService;
import cn.dreamchan.modules.monitor.pojo.entity.LoginLogEntity;

import org.springframework.stereotype.Service;

/**
 * 系统访问记录 服务实现类
 *
 * @author DreamChan
 */
@Service
public class LoginLogServiceImpl extends BaseService<LoginLogMapper, LoginLogEntity> implements LoginLogService {

}

