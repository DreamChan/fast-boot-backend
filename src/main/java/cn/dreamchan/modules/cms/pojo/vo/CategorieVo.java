package cn.dreamchan.modules.cms.pojo.vo;

import java.time.LocalDateTime;
import cn.afterturn.easypoi.excel.annotation.Excel;

import lombok.Data;


/**
 * 文章分类
 *
 * @author DreamChan
 */
@Data
public class CategorieVo {


	// ID
	@Excel(name = "ID", width = 20)
	private Long categoryId;

	// 分类名称
	@Excel(name = "分类名称", width = 20)
	private String categoryName;

	// 描述
	@Excel(name = "描述", width = 20)
	private String description;

	// 顺序
	@Excel(name = "顺序", width = 20)
	private Integer orderNum;

	// 状态：0表示不使用，1表示正常
	@Excel(name = "状态：0表示不使用，1表示正常", width = 20)
	private String status;

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