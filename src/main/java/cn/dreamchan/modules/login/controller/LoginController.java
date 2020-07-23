package cn.dreamchan.modules.login.controller;

import cn.dreamchan.common.base.BaseController;
import cn.dreamchan.common.biz.R;
import cn.dreamchan.common.biz.ResObject;
import cn.dreamchan.common.constant.Constants;
import cn.dreamchan.component.security.pojo.LoginUserDetails;
import cn.dreamchan.modules.login.pojo.dto.LoginParam;
import cn.dreamchan.modules.login.service.LoginService;
import cn.dreamchan.modules.system.pojo.vo.MenuVo;
import cn.dreamchan.modules.system.pojo.vo.UserVo;
import cn.dreamchan.modules.system.service.MenuService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 登录验证
 *
 * @author DreamChan
 */
@Api(tags = "登录管理")
@RestController
public class LoginController extends BaseController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private MenuService menuService;


    /**
     * 登录方法
     *
     * @param loginParam 登陆信息
     * @return 结果
     */
    @PostMapping("/login")
    public ResObject login(@RequestBody LoginParam loginParam) {

        // 生成令牌
        String token = loginService.login(loginParam);

        Map<String, Object> result = new HashMap<>();
        result.put(Constants.TOKEN, token);
        return R.success(result);
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("/getInfo")
    public ResObject getInfo() {

        LoginUserDetails loginUser = getLoginUser();
        UserVo user = getLoginUser().getUserVo();

        // 角色集合
        Set<String> roles = loginUser.getRoles();
        // 权限集合
        Set<String> permissions = loginUser.getPermissions();

        Map<String, Object> result = new HashMap<>();
        result.put("user", user);
        result.put("roles", roles);
        result.put("permissions", permissions);
        return R.success(result);
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("/getRouters")
    public ResObject getRouters() {
        // 用户信息
        UserVo user = getLoginUser().getUserVo();
        List<MenuVo> menus = menuService.selectMenuTreeByUserId(user.getUserId());
        return R.success(menuService.buildRouters(menus));
    }

}
