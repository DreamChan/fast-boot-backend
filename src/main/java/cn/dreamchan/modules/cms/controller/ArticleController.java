package cn.dreamchan.modules.cms.controller;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.dreamchan.common.biz.ResObject;
import cn.dreamchan.common.utils.SecurityUtils;
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
import cn.dreamchan.modules.cms.service.ArticleService;


/**
 * 文章管理
 *
 * @author DreamChan
 */
@Api(tags = "文章管理")
@Slf4j
@RestController
@RequestMapping("/cms/article")
public class ArticleController extends BaseController<ArticlePageQueryParam, ArticleEntity> {

	@Autowired
	private ArticleService articleService;
	@Autowired
	private ArticleMapStruct articleMapStruct;

	/**
	* 文章 分页列表
	*/
	@ApiOperation("文章分页列表")
	@PreAuthorize("hasPermission('/cms/article',  'cms:article:query')")
	@GetMapping(value = "/pageList")
	public ResObject pageList(@Valid ArticlePageQueryParam param) {

		Page<ArticleEntity> page = new Page<>(param.getPageNum(), param.getPageSize());
		IPage<ArticleEntity> pageList = articleService.page(page, this.getQueryWrapper(articleMapStruct, param));
		Page<ArticleVo> articleVoPage = articleMapStruct.toVoList(pageList);
		return R.success(articleVoPage);
	}

	/**
	* 获取文章
	*/
	@ApiOperation("获取文章")
	@ApiImplicitParam(name = "articleId", required = true, dataType = "Long", paramType = "path")
	@PreAuthorize("hasPermission('/cms/article',  'cms:article:query')")
	@GetMapping("/{articleId}")
	public ResObject get(@PathVariable Long articleId) {
		ArticleEntity article = articleService.getById(articleId);
		return R.success(articleMapStruct.toVo(article));
	}

	/**
	* 新增文章
	*/
	@ApiOperation("新增文章")
	@ApiImplicitParam(name = "ArticleEditParam ", value = "新增文章", dataType = "ArticleEditParam")
	@PreAuthorize("hasPermission('/cms/article',  'cms:article:add')")
	@EventLog(message = "新增文章", businessType = EventLogEnum.CREATE)
	@PostMapping
	public ResObject save(@Valid @RequestBody ArticleEditParam articleEditParam) {
		ArticleEntity article = articleMapStruct.toEntity(articleEditParam);
		article.setUserId(SecurityUtils.getUsername());
		return R.toRes(articleService.save(article));
	}

	/**
	* 修改文章
	*/
	@ApiOperation("修改文章")
	@ApiImplicitParam(name = "ArticleEditParam ", value = "修改文章", dataType = "ArticleEditParam")
	@PreAuthorize("hasPermission('/cms/article',  'cms:article:edit')")
	@EventLog(message = "修改文章", businessType = EventLogEnum.UPDATE)
	@PutMapping
	public ResObject edit(@Valid @RequestBody ArticleEditParam articleEditParam) {
		ArticleEntity article = articleMapStruct.toEntity(articleEditParam);
		return R.toRes(articleService.updateById(article));
	}

	/**
	* 删除文章
	*/
	@ApiOperation("删除文章")
	@ApiImplicitParam(name = "articleId", required = true, dataType = "Long", paramType = "path")
	@PreAuthorize("hasPermission('/cms/article',  'cms:article:delete')")
	@EventLog(message = "删除文章", businessType = EventLogEnum.DELETE)
	@DeleteMapping("/{articleIds}")
	public ResObject delete(@PathVariable Long[] articleIds) {
		return R.toRes(articleService.removeByIds(Arrays.asList(articleIds)));
	}

	/**
	* 导出文章
	*/
	@ApiOperation("导出文章")
	@PreAuthorize("hasPermission('/cms/article',  'cms:article:export')")
	@SneakyThrows
	@EventLog(message = "导出文章", businessType = EventLogEnum.EXPORT)
	@PostMapping(value = "/exportXls")
	public void exportXls(ArticlePageQueryParam param, HttpServletResponse response) {
		List<ArticleEntity> list = articleService.list(this.getQueryWrapper(articleMapStruct, param));
		List<ArticleVo> articleVoList = articleMapStruct.toVoList(list);
		ExcelUtils.exportExcel(articleVoList, ArticleVo.class, "文章", new ExportParams(), response);
	}

}
