package cn.dreamchan.component.event;

import cn.dreamchan.modules.monitor.pojo.entity.LoginLogEntity;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 登录日志
 *
 * @author DreamChan
 */
public class LoginLogEvent extends ApplicationEvent {

	private LoginLogEntity loginLogEntity;

	public LoginLogEvent(Object source, LoginLogEntity loginLogEntity) {
		super(source);
		this.loginLogEntity = loginLogEntity;
	}

	public LoginLogEntity getLoginLogEntity() {
		return loginLogEntity;
	}
}
