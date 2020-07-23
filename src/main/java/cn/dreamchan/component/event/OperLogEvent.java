package cn.dreamchan.component.event;

import cn.dreamchan.modules.monitor.pojo.entity.OperLogEntity;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 操作日志
 *
 * @author DreamChan
 */
public class OperLogEvent extends ApplicationEvent {

	private OperLogEntity operLogEntity;

	public OperLogEvent(Object source, OperLogEntity operLogEntity) {
		super(source);
		this.operLogEntity = operLogEntity;
	}

	public OperLogEntity getOperLogEntity() {
		return operLogEntity;
	}
}
