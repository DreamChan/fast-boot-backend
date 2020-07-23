package cn.dreamchan.modules.system.mapper;

import cn.dreamchan.modules.system.pojo.entity.DeptEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 部门 Mapper 接口
 *
 * @author DreamChan
 */
public interface DeptMapper extends BaseMapper<DeptEntity> {

    List<Integer> selectDeptListByRoleId(Long roleId);
}

