package cn.dreamchan.modules.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;


import cn.dreamchan.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 用户与岗位关联
 *
 * @author DreamChan
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user_post")
public class UserPostEntity extends BaseEntity{


	// 用户ID
	private Long userId;

	// 岗位ID
	private Long postId;

}