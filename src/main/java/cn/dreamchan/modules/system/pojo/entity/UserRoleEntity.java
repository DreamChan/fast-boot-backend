package cn.dreamchan.modules.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;


import cn.dreamchan.common.base.BaseEntity;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 用户和角色关联
 *
 * @author DreamChan
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user_role")
public class UserRoleEntity extends BaseEntity {


	// 用户ID
	private Long userId;

	// 角色ID
	private Long roleId;

}