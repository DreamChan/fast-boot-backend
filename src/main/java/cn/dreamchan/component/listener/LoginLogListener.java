package cn.dreamchan.component.listener;

import cn.dreamchan.component.event.LoginLogEvent;
import cn.dreamchan.modules.monitor.pojo.entity.LoginLogEntity;
import cn.dreamchan.modules.monitor.service.LoginLogService;
import cn.dreamchan.modules.system.pojo.entity.UserEntity;
import cn.dreamchan.modules.system.service.UserService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 登录日志 监听处理
 *
 * @author DreamChan
 */
@Component
public class LoginLogListener {

	@Autowired
	private LoginLogService loginLogService;

	@Autowired
	private UserService userService;



	@Async
	@EventListener(LoginLogEvent.class)
	public void service(LoginLogEvent event) {

		LoginLogEntity loginLogEntity = event.getLoginLogEntity();

		// 保存登录日志
		loginLogService.save(loginLogEntity);

		// 更新用户登录时间
		UserEntity userEntity = new UserEntity();
		userEntity.setUserId(loginLogEntity.getUserId());
		userEntity.setLoginIp(loginLogEntity.getIpaddr());
		userEntity.setLoginDate(loginLogEntity.getLoginTime());
		userService.updateById(userEntity);
	}



}
