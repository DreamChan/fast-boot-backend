package cn.dreamchan.modules.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;


import cn.dreamchan.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 角色和菜单关联
 *
 * @author DreamChan
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_role_menu")
public class RoleMenuEntity extends BaseEntity{

	// 角色ID
	private Long roleId;

	// 菜单ID
	private Long menuId;

}