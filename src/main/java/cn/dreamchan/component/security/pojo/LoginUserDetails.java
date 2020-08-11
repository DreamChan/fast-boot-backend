package cn.dreamchan.component.security.pojo;

import cn.dreamchan.common.constant.Constants;
import cn.dreamchan.modules.system.pojo.vo.UserVo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 登录用户
 *
 * @author DreamChan
 */
@Data
public class LoginUserDetails implements UserDetails {

    /**
     * 用户唯一标识
     */
    private String token;

    /**
     * 登陆时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     * 登录IP地址
     */
    private String ipaddr;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 角色列表
     */
    private Set<String> roles;

    /**
     * 权限列表
     */
    private Set<String> permissions;

    /**
     * 用户信息
     */
    private UserVo userVo;


    public LoginUserDetails() {
    }

    public LoginUserDetails(UserVo userVo, Set<String> permissions, Set<String> roles) {
        this.userVo = userVo;
        this.roles = roles;
        this.permissions = permissions;
    }

    /**
     * 返回当前用户的角色列表
     * @return
     */
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的角色
        return userVo.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(Constants.DEFAULT_ROLE_PREFIX + role.getRoleKey()))
                .collect(Collectors.toList());
    }


    @JsonIgnore
    @Override
    public String getPassword() {
        return userVo.getPassword();
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return userVo.getUserName();
    }

    /**
     * 账户是否未过期,过期无法验证
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    /**
     * 指定用户是否解锁,锁定的用户无法进行身份验证
     *
     * @return
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    /**
     * 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
     *
     * @return
     */
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    /**
     * 是否可用 ,禁用的用户不能身份验证
     *
     * @return
     */
    @JsonIgnore
    @Override
    public boolean isEnabled()
    {
        return true;
    }

}
