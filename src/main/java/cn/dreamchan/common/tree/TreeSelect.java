package cn.dreamchan.common.tree;

import cn.dreamchan.modules.system.pojo.vo.DeptVo;
import cn.dreamchan.modules.system.pojo.vo.MenuVo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Treeselect 树结构实体类
 *
 * @author DreamChan
 */
@Data
public class TreeSelect implements Serializable {
    /**
     * 节点ID
     */
    private Long id;

    /**
     * 节点名称
     */
    private String label;

    /**
     * 子节点
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelect> children;

    public TreeSelect() {
    }

    public TreeSelect(DeptVo dept) {
        this.id = dept.getDeptId();
        this.label = dept.getDeptName();
        this.children = dept.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    public TreeSelect(MenuVo menu) {
        this.id = menu.getMenuId();
        this.label = menu.getMenuName();
        this.children = menu.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }
}
