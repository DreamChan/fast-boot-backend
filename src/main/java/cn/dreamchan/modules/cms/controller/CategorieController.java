package cn.dreamchan.modules.cms.controller;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.dreamchan.common.biz.ResObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Arrays;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import cn.dreamchan.common.biz.R;
import cn.dreamchan.component.aop.annotation.EventLog;
import cn.dreamchan.common.enums.EventLogEnum;
import cn.dreamchan.common.utils.ExcelUtils;
import io.swagger.annotations.Api;
import cn.dreamchan.common.base.BaseController;
import cn.dreamchan.modules.cms.pojo.entity.*;
import cn.dreamchan.modules.cms.pojo.vo.*;
import cn.dreamchan.modules.cms.pojo.dto.*;
import cn.dreamchan.modules.cms.service.mapstruct.*;
import cn.dreamchan.modules.cms.service.CategorieService;


/**
 * 文章分类管理
 *
 * @author DreamChan
 */
@Api(tags = "文章分类管理")
@Slf4j
@RestController
@RequestMapping("/cms/categorie")
public class CategorieController extends BaseController<CategoriePageQueryParam, CategorieEntity> {

	@Autowired
	private CategorieService categorieService;
	@Autowired
	private CategorieMapStruct categorieMapStruct;

	/**
	* 文章分类 分页列表
	*/
	@ApiOperation("文章分类分页列表")
	@PreAuthorize("hasPermission('/cms/categorie',  'cms:categorie:query')")
	@GetMapping(value = "/pageList")
	public ResObject pageList(@Valid CategoriePageQueryParam param) {

		Page<CategorieEntity> page = new Page<>(param.getPageNum(), param.getPageSize());
		IPage<CategorieEntity> pageList = categorieService.page(page, this.getQueryWrapper(categorieMapStruct, param));
		Page<CategorieVo> categorieVoPage = categorieMapStruct.toVoList(pageList);
		return R.success(categorieVoPage);
	}

	/**
	 * 文章分类 全部列表
	 */
	@ApiOperation("文章分类全部列表")
	@PreAuthorize("hasPermission('/cms/categorie',  'cms:categorie:query')")
	@GetMapping(value = "/allList")
	public ResObject allList(@Valid CategoriePageQueryParam param) {

		List<CategorieEntity> allList = categorieService.list(this.getQueryWrapper(categorieMapStruct, param));
		List<CategorieVo> list = categorieMapStruct.toVoList(allList);
		return R.success(list);
	}

	/**
	* 获取文章分类
	*/
	@ApiOperation("获取文章分类")
	@ApiImplicitParam(name = "categoryId", required = true, dataType = "Long", paramType = "path")
	@PreAuthorize("hasPermission('/cms/categorie',  'cms:categorie:query')")
	@GetMapping("/{categoryId}")
	public ResObject get(@PathVariable Long categoryId) {
		CategorieEntity categorie = categorieService.getById(categoryId);
		return R.success(categorieMapStruct.toVo(categorie));
	}

	/**
	* 新增文章分类
	*/
	@ApiOperation("新增文章分类")
	@ApiImplicitParam(name = "CategorieEditParam ", value = "新增文章分类", dataType = "CategorieEditParam")
	@PreAuthorize("hasPermission('/cms/categorie',  'cms:categorie:add')")
	@EventLog(message = "新增文章分类", businessType = EventLogEnum.CREATE)
	@PostMapping
	public ResObject save(@Valid @RequestBody CategorieEditParam categorieEditParam) {
		CategorieEntity categorie = categorieMapStruct.toEntity(categorieEditParam);
		return R.toRes(categorieService.save(categorie));
	}

	/**
	* 修改文章分类
	*/
	@ApiOperation("修改文章分类")
	@ApiImplicitParam(name = "CategorieEditParam ", value = "修改文章分类", dataType = "CategorieEditParam")
	@PreAuthorize("hasPermission('/cms/categorie',  'cms:categorie:edit')")
	@EventLog(message = "修改文章分类", businessType = EventLogEnum.UPDATE)
	@PutMapping
	public ResObject edit(@Valid @RequestBody CategorieEditParam categorieEditParam) {
		CategorieEntity categorie = categorieMapStruct.toEntity(categorieEditParam);
		return R.toRes(categorieService.updateById(categorie));
	}

	/**
	* 删除文章分类
	*/
	@ApiOperation("删除文章分类")
	@ApiImplicitParam(name = "categoryId", required = true, dataType = "Long", paramType = "path")
	@PreAuthorize("hasPermission('/cms/categorie',  'cms:categorie:delete')")
	@EventLog(message = "删除文章分类", businessType = EventLogEnum.DELETE)
	@DeleteMapping("/{categoryIds}")
	public ResObject delete(@PathVariable Long[] categoryIds) {
		return R.toRes(categorieService.removeByIds(Arrays.asList(categoryIds)));
	}

	/**
	* 导出文章分类
	*/
	@ApiOperation("导出文章分类")
	@PreAuthorize("hasPermission('/cms/categorie',  'cms:categorie:export')")
	@SneakyThrows
	@EventLog(message = "导出文章分类", businessType = EventLogEnum.EXPORT)
	@PostMapping(value = "/exportXls")
	public void exportXls(CategoriePageQueryParam param, HttpServletResponse response) {
		List<CategorieEntity> list = categorieService.list(this.getQueryWrapper(categorieMapStruct, param));
		List<CategorieVo> categorieVoList = categorieMapStruct.toVoList(list);
		ExcelUtils.exportExcel(categorieVoList, CategorieVo.class, "文章分类", new ExportParams(), response);
	}

}
