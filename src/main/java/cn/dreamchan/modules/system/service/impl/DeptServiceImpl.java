package cn.dreamchan.modules.system.service.impl;

import cn.dreamchan.common.constant.Constants;
import cn.dreamchan.common.enums.StatusEnum;
import cn.dreamchan.common.exception.CustomException;
import cn.dreamchan.common.tree.TreeSelect;
import cn.dreamchan.common.utils.StringUtils;
import cn.dreamchan.common.utils.TreeUtil;
import cn.dreamchan.modules.system.mapper.DeptMapper;
import cn.dreamchan.modules.system.pojo.vo.DeptVo;
import cn.dreamchan.modules.system.service.DeptService;
import cn.dreamchan.common.base.BaseService;
import cn.dreamchan.modules.system.pojo.entity.DeptEntity;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 部门 服务实现类
 *
 * @author DreamChan
 */
@Service
public class DeptServiceImpl extends BaseService<DeptMapper, DeptEntity> implements DeptService {

    /**
     * 根据角色查询所有的部门
     * @param roleId
     * @return
     */
    @Override
    public List<Integer> selectDeptListByRoleId(Long roleId) {
        return this.baseMapper.selectDeptListByRoleId(roleId);
    }

    @Override
    public List<TreeSelect> buildDeptTreeSelect(List<DeptVo> depts) {
        List<DeptVo> deptTrees = TreeUtil.build(depts, Constants.DEPT_ROOT_ID);
        return deptTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 新增保存部门信息
     *
     * @param deptEntity 部门信息
     * @return 结果
     */
    @Override
    public int insertDept(DeptEntity deptEntity) {
        DeptEntity parentDeptEntity = this.baseMapper.selectById(deptEntity.getParentId());
        // 如果父节点不为正常状态,则不允许新增子节点
        if (parentDeptEntity.getStatus() == StatusEnum.DISABLE.getCode()) {
            throw new CustomException("部门停用，不允许新增");
        }
        deptEntity.setParentIds(parentDeptEntity.getParentIds() + "," + deptEntity.getParentId());
        return this.baseMapper.insert(deptEntity);
    }
}

