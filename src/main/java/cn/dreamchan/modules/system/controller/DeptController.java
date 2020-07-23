package cn.dreamchan.modules.system.controller;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.dreamchan.common.biz.ResObject;
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
import java.util.*;

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
import cn.dreamchan.modules.system.service.DeptService;


/**
 * 部门管理
 *
 * @author DreamChan
 */
@Api(tags = "部门管理")
@Slf4j
@RestController
@RequestMapping("/system/dept")
public class DeptController extends BaseController<DeptPageQueryParam, DeptEntity> {

    @Autowired
    private DeptService deptService;
    @Autowired
    private DeptMapStruct deptMapStruct;

    /**
     * 部门 分页列表
     */
    @ApiOperation("部门分页列表")
    @PreAuthorize("hasPermission('/system/dept',  'system:dept:query')")
    @GetMapping(value = "/pageList")
    public ResObject pageList(@Valid DeptPageQueryParam param) {
        Page<DeptEntity> page = new Page<>(param.getPageNum(), param.getPageSize());
        IPage<DeptEntity> pageList = deptService.page(page, this.getQueryWrapper(deptMapStruct, param));
        Page<DeptVo> deptVoPage = deptMapStruct.toVoList(pageList);
        return R.success(deptVoPage);
    }


    /**
     * 部门列表
     */
    @ApiOperation("部门列表")
    @PreAuthorize("hasPermission('/system/dept',  'system:dept:query')")
    @GetMapping(value = "/allList")
    public ResObject allList() {
        List<DeptEntity> allList = deptService.list();
        List<DeptVo> allVoList = deptMapStruct.toVoList(allList);
        return R.success(allVoList);
    }

    /**
     * 获取部门
     */
    @ApiOperation("获取部门")
    @ApiImplicitParam(name = "deptId", required = true, dataType = "Long", paramType = "path")
    @PreAuthorize("hasPermission('/system/dept',  'system:dept:query')")
    @GetMapping("/{deptId}")
    public ResObject get(@PathVariable Long deptId) {
        DeptEntity dept = deptService.getById(deptId);
        return R.success(dept);
    }

    /**
     * 新增部门
     */
    @ApiOperation("新增部门")
    @ApiImplicitParam(name = "DeptEditParam ", value = "新增部门", dataType = "DeptEditParam")
    @PreAuthorize("hasPermission('/system/dept',  'system:dept:add')")
    @EventLog(message = "新增部门", businessType = EventLogEnum.CREATE)
    @PostMapping
    public ResObject save(@Valid @RequestBody DeptEditParam deptEditParam) {
        DeptEntity deptEntity = deptService.lambdaQuery().eq(DeptEntity::getDeptName, deptEditParam.getDeptName())
                                    .eq(DeptEntity::getParentId, deptEditParam.getParentId()).one();
        if (deptEntity != null) {
            return R.failure("部门名称已存在, 请使用其他部门名称");
        }

        DeptEntity dept = deptMapStruct.toEntity(deptEditParam);
        return R.toRes(deptService.insertDept(dept));
    }

    /**
     * 修改部门
     */
    @ApiOperation("修改部门")
    @ApiImplicitParam(name = "DeptEditParam ", value = "修改部门", dataType = "DeptEditParam")
    @PreAuthorize("hasPermission('/system/dept',  'system:dept:edit')")
    @EventLog(message = "修改部门", businessType = EventLogEnum.UPDATE)
    @PutMapping
    public ResObject edit(@Valid @RequestBody DeptEditParam deptEditParam) {
        DeptEntity dept = deptMapStruct.toEntity(deptEditParam);
        return R.toRes(deptService.updateById(dept));
    }

    /**
     * 删除部门
     */
    @ApiOperation("删除部门")
    @ApiImplicitParam(name = "deptId", required = true, dataType = "Long", paramType = "path")
    @PreAuthorize("hasPermission('/system/dept',  'system:dept:delete')")
    @EventLog(message = "删除部门", businessType = EventLogEnum.DELETE)
    @DeleteMapping("/{deptIds}")
    public ResObject delete(@PathVariable Long[] deptIds) {
        return R.toRes(deptService.removeByIds(Arrays.asList(deptIds)));
    }

    /**
     * 导出部门
     */
    @ApiOperation("导出部门")
    @PreAuthorize("hasPermission('/system/dept',  'system:dept:export')")
    @SneakyThrows
    @EventLog(message = "导出部门", businessType = EventLogEnum.EXPORT)
    @PostMapping(value = "/exportXls")
    public void exportXls(DeptPageQueryParam param, HttpServletResponse response) {
        List<DeptEntity> list = deptService.list(this.getQueryWrapper(deptMapStruct, param));
        List<DeptVo> deptVoList = deptMapStruct.toVoList(list);
        ExcelUtils.exportExcel(deptVoList, DeptVo.class, "部门", new ExportParams(), response);
    }

    /**
     * 获取部门下拉树列表
     */
    @GetMapping("/treeselect")
    public ResObject treeselect(@Valid DeptPageQueryParam param) {
        List<DeptEntity> depts = deptService.list(this.getQueryWrapper(deptMapStruct, param));
        List<DeptVo> deptVos = deptMapStruct.toVoList(depts);
        return R.success(deptService.buildDeptTreeSelect(deptVos));
    }

    /**
     * 加载对应角色部门列表树
     */
    @GetMapping(value = "/roleDeptTreeselect/{roleId}")
    public ResObject roleDeptTreeselect(@PathVariable("roleId") Long roleId) {
        List<DeptEntity> list = deptService.list();
        List<DeptVo> deptVos = deptMapStruct.toVoList(list);

        Map result = new HashMap();
        result.put("checkedKeys", deptService.selectDeptListByRoleId(roleId));
        result.put("depts", deptService.buildDeptTreeSelect(deptVos));
        return R.success(result);
    }

}
