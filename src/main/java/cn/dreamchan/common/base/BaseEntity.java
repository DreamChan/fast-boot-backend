package cn.dreamchan.common.base;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;


import java.io.Serializable;
import java.util.Map;

/**
 * Entity 基类
 *
 * @author DreamChan
 */
@Data
public class BaseEntity implements Serializable {

    // 请求参数
    @TableField(exist = false)
    private Map<String, Object> params;

}
