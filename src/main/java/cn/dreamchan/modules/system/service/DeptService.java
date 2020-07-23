package cn.dreamchan.modules.system.service;

import cn.dreamchan.common.tree.TreeSelect;
import cn.dreamchan.modules.system.pojo.vo.DeptVo;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.dreamchan.modules.system.pojo.entity.DeptEntity;

import java.util.List;

/**
 *
 * 部门 服务类
 *
 * @author DreamChan
 */
public interface DeptService extends IService<DeptEntity>{

    int insertDept(DeptEntity dept);

    List<Integer> selectDeptListByRoleId(Long roleId);

    List<TreeSelect> buildDeptTreeSelect(List<DeptVo> depts);

}

