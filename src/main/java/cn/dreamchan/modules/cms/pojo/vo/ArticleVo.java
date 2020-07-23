package cn.dreamchan.modules.cms.pojo.vo;

import java.time.LocalDateTime;
import cn.afterturn.easypoi.excel.annotation.Excel;

import lombok.Data;


/**
 * 文章
 *
 * @author DreamChan
 */
@Data
public class ArticleVo {


	// ID
	@Excel(name = "ID", width = 20)
	private Long articleId;

	// 标题
	@Excel(name = "标题", width = 20)
	private String title;

	// 用户ID
	@Excel(name = "用户ID", width = 20)
	private String userId;

	// 分类ID
	@Excel(name = "分类ID", width = 20)
	private Long categoryId;

	// 文章内容
	@Excel(name = "文章内容", width = 20)
	private String content;

	// 阅读数量
	@Excel(name = "阅读数量", width = 20)
	private Integer viewCount;

	// 状态（0表示已发布，1表示草稿，2表示删除）
	@Excel(name = "状态（0表示已发布，1表示草稿，2表示删除）", width = 20)
	private String state;

	// 创建人
	@Excel(name = "创建人", width = 20)
	private String createBy;

	// 创建时间
	@Excel(name = "创建时间", width = 20)
	private LocalDateTime createTime;

	// 更新人
	@Excel(name = "更新人", width = 20)
	private String updateBy;

	// 更新时间
	@Excel(name = "更新时间", width = 20)
	private LocalDateTime updateTime;

}