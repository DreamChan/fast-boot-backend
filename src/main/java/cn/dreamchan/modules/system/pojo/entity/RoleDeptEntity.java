package cn.dreamchan.modules.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;


import cn.dreamchan.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 角色和部门关联
 *
 * @author DreamChan
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role_dept")
public class RoleDeptEntity extends BaseEntity{


	// 角色ID
	private Long roleId;

	// 部门ID
	private Long deptId;

}