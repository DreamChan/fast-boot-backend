package cn.dreamchan.modules.system.pojo.entity;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.dreamchan.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 字典类型
 *
 * @author DreamChan
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_dict_type")
public class DictTypeEntity extends BaseEntity {


	// 字典主键
	@TableId(value = "dict_type_id" , type = IdType.AUTO)
	private Long dictTypeId;

	// 字典名称
	private String dictName;

	// 字典编码
	private String dictCode;

	// 状态（0正常 1停用）
	private String status;

	// 备注
	private String remark;

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