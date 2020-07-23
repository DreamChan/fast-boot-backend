package cn.dreamchan.modules.system.controller;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.dreamchan.common.biz.ResObject;
import cn.dreamchan.modules.system.service.RoleMenuService;
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
import cn.dreamchan.common.base.BaseController;
import cn.dreamchan.modules.system.pojo.entity.*;
import cn.dreamchan.modules.system.pojo.vo.*;
import cn.dreamchan.modules.system.pojo.dto.*;
import cn.dreamchan.modules.system.service.mapstruct.*;
import cn.dreamchan.modules.system.service.MenuService;


/**
 * 菜单权限管理
 *
 * @author DreamChan
 */
@Api(tags = "菜单权限管理")
@Slf4j
@RestController
@RequestMapping("/system/menu")
public class MenuController extends BaseController<MenuPageQueryParam, MenuEntity> {

    @Autowired
    private MenuService menuService;
    @Autowired
    private MenuMapStruct menuMapStruct;
    @Autowired
    private RoleMenuService roleMenuService;

    /**
     * 菜单权限 分页列表
     */
    @ApiOperation("菜单权限分页列表")
    @PreAuthorize("hasPermission('/system/menu',  'system:menu:query')")
    @GetMapping(value = "/pageList")
    public ResObject pageList(@Valid MenuPageQueryParam param) {

        Page<MenuEntity> page = new Page<>(param.getPageNum(), param.getPageSize());
        IPage<MenuEntity> pageList = menuService.page(page, this.getQueryWrapper(menuMapStruct, param));
        Page<MenuVo> menuVoPage = menuMapStruct.toVoList(pageList);
        return R.success(menuVoPage);
    }

    /**
     * 菜单权限 列表
     */
    @ApiOperation("菜单权限列表")
    @PreAuthorize("hasPermission('/system/menu',  'system:menu:query')")
    @GetMapping(value = "/allList")
    public ResObject allList() {
        List<MenuEntity> allList = menuService.list();
        List<MenuVo> allVoList = menuMapStruct.toVoList(allList);
        return R.success(allVoList);
    }


    /**
     * 获取菜单权限
     */
    @ApiOperation("获取菜单权限")
    @ApiImplicitParam(name = "menuId", required = true, dataType = "Long", paramType = "path")
    @PreAuthorize("hasPermission('/system/menu',  'system:menu:query')")
    @GetMapping("/{menuId}")
    public ResObject get(@PathVariable Long menuId) {
        MenuEntity menu = menuService.getById(menuId);
        return R.success(menu);
    }

    /**
     * 新增菜单权限
     */
    @ApiOperation("新增菜单权限")
    @ApiImplicitParam(name = "MenuEditParam ", value = "新增菜单权限", dataType = "MenuEditParam")
    @PreAuthorize("hasPermission('/system/menu',  'system:menu:add')")
    @EventLog(message = "新增菜单权限", businessType = EventLogEnum.CREATE)
    @PostMapping
    public ResObject save(@Valid @RequestBody MenuEditParam menuEditParam) {
        MenuEntity menu = menuMapStruct.toEntity(menuEditParam);
        return R.toRes(menuService.save(menu));
    }

    /**
     * 修改菜单权限
     */
    @ApiOperation("修改菜单权限")
    @ApiImplicitParam(name = "MenuEditParam ", value = "修改菜单权限", dataType = "MenuEditParam")
    @PreAuthorize("hasPermission('/system/menu',  'system:menu:edit')")
    @EventLog(message = "修改菜单权限", businessType = EventLogEnum.UPDATE)
    @PutMapping
    public ResObject edit(@Valid @RequestBody MenuEditParam menuEditParam) {
        MenuEntity menu = menuMapStruct.toEntity(menuEditParam);
        return R.toRes(menuService.updateById(menu));
    }

    /**
     * 删除菜单权限
     */
    @ApiOperation("删除菜单权限")
    @ApiImplicitParam(name = "menuId", required = true, dataType = "Long", paramType = "path")
    @PreAuthorize("hasPermission('/system/menu',  'system:menu:delete')")
    @EventLog(message = "删除菜单权限", businessType = EventLogEnum.DELETE)
    @DeleteMapping("/{menuId}")
    public ResObject delete(@PathVariable Long menuId) {
        if (menuService.hasChildByMenuId(menuId)) {
            return R.failure("存在子菜单,不允许删除");
        }
        if (roleMenuService.checkMenuExistRole(menuId)) {
            return R.failure("菜单已分配,不允许删除");
        }
        return R.toRes(menuService.removeById(menuId));
    }

    /**
     * 导出菜单权限
     */
    @ApiOperation("导出菜单权限")
    @PreAuthorize("hasPermission('/system/menu',  'system:menu:export')")
    @SneakyThrows
    @EventLog(message = "导出菜单权限", businessType = EventLogEnum.EXPORT)
    @PostMapping(value = "/exportXls")
    public void exportXls(MenuPageQueryParam param, HttpServletResponse response) {
        List<MenuEntity> list = menuService.list(this.getQueryWrapper(menuMapStruct, param));
        List<MenuVo> menuVoList = menuMapStruct.toVoList(list);
        ExcelUtils.exportExcel(menuVoList, MenuVo.class, "菜单权限", new ExportParams(), response);
    }


    /**
     * 获取菜单下拉树列表
     */
    @GetMapping("/treeselect")
    public ResObject treeselect(@Valid MenuPageQueryParam param) {
        Long userId = getLoginUser().getUserVo().getUserId();
        MenuEntity menuEntity = menuMapStruct.pageQueryParamToEntity(param);
        List<MenuVo> menus = menuService.selectMenuList(menuMapStruct.toVo(menuEntity), userId);
        return R.success(menuService.buildMenuTreeSelect(menus));
    }

    /**
     * 加载对应角色菜单列表树
     */
    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    public ResObject roleMenuTreeselect(@PathVariable("roleId") Long roleId) {

        Long userId = getLoginUser().getUserVo().getUserId();
        List<MenuVo> menus = menuService.selectMenuList(userId);

        Map result = new HashMap<>();
        result.put("checkedKeys", menuService.selectMenuListByRoleId(roleId));
        result.put("menus", menuService.buildMenuTreeSelect(menus));
        return R.success(result);
    }
}
