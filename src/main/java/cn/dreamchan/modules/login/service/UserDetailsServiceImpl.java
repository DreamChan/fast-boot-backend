package cn.dreamchan.modules.login.service;

import cn.dreamchan.common.enums.DelEnum;
import cn.dreamchan.common.enums.StatusEnum;
import cn.dreamchan.common.exception.CustomException;
import cn.dreamchan.component.security.pojo.LoginUserDetails;
import cn.dreamchan.modules.system.pojo.vo.UserVo;
import cn.dreamchan.modules.system.service.MenuService;
import cn.dreamchan.modules.system.service.RoleService;
import cn.dreamchan.modules.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;


/**
 * 用户验证处理
 *
 * @author DreamChan
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, CustomException {

        // 查询登录用户信息
        UserVo user = userService.getUserVoByUserName(username);


        if (user == null) {
            log.info("登录用户：F{} 不存在.", username);
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        } else if (DelEnum.DELETED.getCode().equals(user.getDelStatus())) {
            log.info("登录用户：{} 已被删除.", username);
            throw new CustomException("您的账号：" + username + " 已被删除!");
        } else if (StatusEnum.DISABLE.getCode().equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", username);
            throw new CustomException("您的账号：" + username + " 已停用!");
        }

        Set<String> menus = menuService.getMenuListByUserId(user.getUserId());
        Set<String> roles = roleService.getRoleListByUserId(user.getUserId());

        return new LoginUserDetails(user, menus, roles);
    }

}
