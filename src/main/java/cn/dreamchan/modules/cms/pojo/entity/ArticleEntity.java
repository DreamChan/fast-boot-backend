package cn.dreamchan.modules.cms.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import cn.dreamchan.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 文章
 *
 * @author DreamChan
 */
@EqualsAndHashCode(callSuper = false)
@Data
@TableName("cms_article")
public class ArticleEntity extends BaseEntity {


	// ID
	@TableId(value = "article_id" , type = IdType.AUTO)
	private Long articleId;

	// 标题
	private String title;

	// 用户ID
	private String userId;

	// 分类ID
	private Long categoryId;

	// 文章内容
	private String content;

	// 阅读数量
	private Integer viewCount;

	// 状态（0表示已发布，1表示草稿，2表示删除）
	private String state;

	// 创建人
	@TableField(fill = FieldFill.INSERT)
	private String createBy;

	// 创建时间
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	// 更新人
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateBy;

	// 更新时间
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime;

}