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
 * 文章分类
 *
 * @author DreamChan
 */
@EqualsAndHashCode(callSuper = false)
@Data
@TableName("cms_categorie")
public class CategorieEntity extends BaseEntity {


	// ID
	@TableId(value = "category_id" , type = IdType.AUTO)
	private Long categoryId;

	// 分类名称
	private String categoryName;

	// 描述
	private String description;

	// 顺序
	private Integer orderNum;

	// 状态：0表示不使用，1表示正常
	private String status;

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