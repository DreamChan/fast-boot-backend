package cn.dreamchan.modules.cms.pojo.dto;

import java.time.LocalDateTime;
import lombok.Data;


/**
 * 文章
 *
 * @author DreamChan
 */
@Data
public class ArticleEditParam {


    // ID
    private Long articleId;

    // 标题
    private String title;

    // 用户ID
    private String userId;

    // 分类ID
    private Long categoryId;

    // 文章内容
    private String content;

    // 阅读数量
    private Integer viewCount;

    // 状态（0表示已发布，1表示草稿，2表示删除）
    private String state;

    // 创建人
    private String createBy;

    // 创建时间
    private LocalDateTime createTime;

    // 更新人
    private String updateBy;

    // 更新时间
    private LocalDateTime updateTime;


}