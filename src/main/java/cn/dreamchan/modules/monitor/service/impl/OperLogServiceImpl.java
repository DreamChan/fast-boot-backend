package cn.dreamchan.modules.monitor.service.impl;

import cn.dreamchan.modules.monitor.mapper.OperLogMapper;
import cn.dreamchan.modules.monitor.service.OperLogService;
import cn.dreamchan.common.base.BaseService;
import cn.dreamchan.modules.monitor.pojo.entity.OperLogEntity;

import org.springframework.stereotype.Service;

/**
 * 操作日志记录 服务实现类
 *
 * @author DreamChan
 */
@Service
public class OperLogServiceImpl extends BaseService<OperLogMapper, OperLogEntity> implements OperLogService {

}

