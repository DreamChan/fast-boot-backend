package cn.dreamchan.modules.cms.pojo.dto;

import java.time.LocalDateTime;
import lombok.Data;


/**
 * 文章分类
 *
 * @author DreamChan
 */
@Data
public class CategorieEditParam {


    // ID
    private Long categoryId;

    // 分类名称
    private String categoryName;

    // 描述
    private String description;

    // 顺序
    private Integer orderNum;

    // 状态：0表示不使用，1表示正常
    private String status;

    // 创建人
    private String createBy;

    // 创建时间
    private LocalDateTime createTime;

    // 更新人
    private String updateBy;

    // 更新时间
    private LocalDateTime updateTime;


}