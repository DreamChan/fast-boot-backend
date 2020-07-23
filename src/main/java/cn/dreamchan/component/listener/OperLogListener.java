package cn.dreamchan.component.listener;

import cn.dreamchan.component.event.OperLogEvent;
import cn.dreamchan.modules.monitor.service.OperLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 操作日志 监听处理
 *
 * @author DreamChan
 */
@Slf4j
@Component
public class OperLogListener {

	@Autowired
	private OperLogService operLogService;

	@Async
	@EventListener(OperLogEvent.class)
	public void service(OperLogEvent event) {

		operLogService.save(event.getOperLogEntity());
	}

}
