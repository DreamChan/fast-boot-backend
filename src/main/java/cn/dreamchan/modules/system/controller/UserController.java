package cn.dreamchan.modules.system.controller;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.dreamchan.common.biz.ResObject;
import cn.dreamchan.common.constant.Constants;
import cn.dreamchan.common.utils.SecurityUtils;
import cn.dreamchan.modules.system.service.PostService;
import cn.dreamchan.modules.system.service.RoleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.Map;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import cn.dreamchan.common.biz.R;
import cn.dreamchan.component.aop.annotation.EventLog;
import cn.dreamchan.common.enums.EventLogEnum;
import cn.dreamchan.common.utils.ExcelUtils;
import cn.dreamchan.common.utils.StringUtils;
import cn.dreamchan.common.base.BaseController;
import cn.dreamchan.modules.system.pojo.entity.*;
import cn.dreamchan.modules.system.pojo.vo.*;
import cn.dreamchan.modules.system.pojo.dto.*;
import cn.dreamchan.modules.system.service.mapstruct.*;
import cn.dreamchan.modules.system.service.UserService;


/**
 * 用户信息管理
 *
 * @author DreamChan
 */
@Api(tags = "用户信息管理")
@Slf4j
@RestController
@RequestMapping("/system/user")
public class UserController extends BaseController<UserPageQueryParam, UserEntity> {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapStruct userMapStruct;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PostService postService;

    /**
     * 用户信息 分页列表
     */
    @ApiOperation("用户信息分页列表")
    @PreAuthorize("hasPermission('/system/user',  'system:user:query')")
    @GetMapping(value = "/pageList")
    public ResObject pageList(@Valid UserPageQueryParam param) {

        Page<UserEntity> page = new Page<>(param.getPageNum(), param.getPageSize());
        IPage<UserVo> pageList = userService.getUserList(page, this.getQueryWrapper(userMapStruct, param));
        return R.success(pageList);
    }

    /**
     * 获取用户信息
     */
    @ApiOperation("获取用户信息")
    @ApiImplicitParam(name = "userId", required = true, dataType = "Long", paramType = "path")
    @PreAuthorize("hasPermission('/system/user',  'system:user:query')")
    @GetMapping(value = {"/", "/{userId}"})
    public ResObject getInfo(@PathVariable(value = "userId", required = false) Long userId) {
        UserEntity user = userService.getById(userId);
        UserVo userVo = userMapStruct.toVo(user);
        Map res = new HashMap<>();
        List<RoleEntity> roles = roleService.list();
        res.put("roles", roles);
        res.put("posts", postService.list());
        if (StringUtils.isNotNull(userId)) {
            res.put("postIds", postService.selectPostListByUserId(userId));
            res.put("roleIds", roleService.selectRoleListByUserId(userId));
        }
        res.put("user", userVo);
        return R.success(res);
    }

    /**
     * 新增用户信息
     */
    @ApiOperation("新增用户信息")
    @ApiImplicitParam(name = "UserEditParam ", value = "新增用户信息", dataType = "UserEditParam")
    @PreAuthorize("hasPermission('/system/user',  'system:user:add')")
    @EventLog(message = "新增用户信息", businessType = EventLogEnum.CREATE)
    @PostMapping
    public ResObject save(@Valid @RequestBody UserEditParam userEditParam) {
        UserEntity userEntity = userService.lambdaQuery().eq(UserEntity::getUserName, userEditParam.getUserName()).one();
        if (userEntity != null) {
            return R.failure("用户账号已存在, 请使用其他账号名");
        }

        userEditParam.setPassword(SecurityUtils.encryptPassword(userEditParam.getPassword()));

        return R.toRes(userService.insertUser(userEditParam));
    }

    /**
     * 修改用户信息
     */
    @ApiOperation("修改用户信息")
    @ApiImplicitParam(name = "UserEditParam ", value = "修改用户信息", dataType = "UserEditParam")
    @PreAuthorize("hasPermission('/system/user',  'system:user:edit')")
    @EventLog(message = "修改用户信息", businessType = EventLogEnum.UPDATE)
    @PutMapping
    public ResObject edit(@Valid @RequestBody UserEditParam userEditParam) {

        return R.toRes(userService.updateUser(userEditParam));
    }

    /**
     * 删除用户信息
     */
    @ApiOperation("删除用户信息")
    @ApiImplicitParam(name = "userId", required = true, dataType = "Long", paramType = "path")
    @PreAuthorize("hasPermission('/system/user',  'system:user:delete')")
    @EventLog(message = "删除用户信息", businessType = EventLogEnum.DELETE)
    @DeleteMapping("/{userIds}")
    public ResObject delete(@PathVariable Long[] userIds) {
        // 管理员admin账号不能删除
        List<Long> userIdList = Arrays.asList(userIds);
        userIdList.remove(Constants.ADMIN_USER_ID);

        return R.toRes(userService.removeByIds(userIdList));
    }

    /**
     * 导出用户信息
     */
    @ApiOperation("导出用户信息")
    @PreAuthorize("hasPermission('/system/user',  'system:user:export')")
    @SneakyThrows
    @EventLog(message = "导出用户信息", businessType = EventLogEnum.EXPORT)
    @PostMapping(value = "/exportXls")
    public void exportXls(UserPageQueryParam param, HttpServletResponse response) {
        List<UserEntity> list = userService.list(this.getQueryWrapper(userMapStruct, param));
        List<UserVo> userVoList = userMapStruct.toVoList(list);
        ExcelUtils.exportExcel(userVoList, UserVo.class, "用户信息", new ExportParams(), response);
    }

    /**
     * 状态修改
     */
    @PreAuthorize("hasPermission('/system/user',  'system:user:edit')")
    @EventLog(message = "修改用户信息", businessType = EventLogEnum.UPDATE)
    @PutMapping("/changeStatus")
    public ResObject changeStatus(@RequestBody UserEditParam userEditParam) {
        userService.checkUserAllowed(userEditParam);

        UserEntity user = new UserEntity();
        user.setUserId(userEditParam.getUserId());
        user.setStatus(userEditParam.getStatus());
        return R.toRes(userService.updateById(user));
    }

    /**
     * 重置密码
     */
    @PreAuthorize("hasPermission('/system/user',  'system:user:edit')")
    @EventLog(message = "重置密码", businessType = EventLogEnum.UPDATE)
    @PutMapping("/resetPwd")
    public ResObject resetPwd(@RequestBody UserEditParam userEditParam) {
        userService.checkUserAllowed(userEditParam);

        UserEntity user = userMapStruct.toEntity(userEditParam);
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));

        return R.toRes(userService.updateById(user));
    }
}
