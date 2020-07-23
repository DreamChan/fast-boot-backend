package cn.dreamchan.modules.login.pojo.dto;

import lombok.Data;

/**
 * 登录参数
 *
 * @author DreamChan
 */
@Data
public class LoginParam {
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 验证码
     */
    private String code;

    /**
     * 唯一标识
     */
    private String uuid;


}
