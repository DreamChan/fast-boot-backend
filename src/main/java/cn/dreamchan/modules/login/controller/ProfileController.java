package cn.dreamchan.modules.login.controller;

import cn.dreamchan.common.base.BaseController;
import cn.dreamchan.common.biz.R;
import cn.dreamchan.common.biz.ResObject;
import cn.dreamchan.common.utils.SecurityUtils;
import cn.dreamchan.component.aop.annotation.EventLog;
import cn.dreamchan.common.enums.EventLogEnum;
import cn.dreamchan.component.security.pojo.LoginUserDetails;
import cn.dreamchan.modules.login.service.TokenService;
import cn.dreamchan.modules.system.pojo.dto.UserEditParam;
import cn.dreamchan.modules.system.pojo.entity.UserEntity;
import cn.dreamchan.modules.system.pojo.vo.UserVo;
import cn.dreamchan.modules.system.service.UserService;
import cn.dreamchan.modules.system.service.mapstruct.UserMapStruct;
import com.jlefebure.spring.boot.minio.MinioConfigurationProperties;
import com.jlefebure.spring.boot.minio.MinioException;
import com.jlefebure.spring.boot.minio.MinioService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * 个人信息
 *
 * @author DreamChan
 */
@Api(tags = "个人信息管理")
@RestController
@RequestMapping("/system/user/profile")
public class ProfileController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserMapStruct userMapStruct;
    @Autowired
    private MinioService minioService;
    @Autowired
    private MinioConfigurationProperties minioConfig;

    /**
     * 个人信息
     */
    @GetMapping
    public ResObject profile() {
        UserVo user = getLoginUser().getUserVo();

        Map result = new HashMap();
        result.put("user", user);
        result.put("roleGroup", userService.selectUserRoleGroup(user.getUserName()));
        result.put("postGroup", userService.selectUserPostGroup(user.getUserName()));
        return R.success(result);
    }

    /**
     * 修改用户
     */
    @EventLog(message = "个人信息", businessType = EventLogEnum.UPDATE)
    @PutMapping
    public ResObject updateProfile(@RequestBody UserEditParam user) {
        UserEntity userEntity = userMapStruct.toEntity(user);
        if (userService.updateUserProfile(userEntity)) {
            LoginUserDetails loginUser = getLoginUser();
            // 更新缓存用户信息
            loginUser.getUserVo().setNickName(user.getNickName());
            loginUser.getUserVo().setPhone(user.getPhone());
            loginUser.getUserVo().setEmail(user.getEmail());
            loginUser.getUserVo().setSex(user.getSex());
            tokenService.setLoginUser(loginUser);
            return R.success();
        }
        return R.failure("修改个人信息异常，请联系管理员");
    }

    /**
     * 重置密码
     */
    @EventLog(message = "重置密码", businessType = EventLogEnum.UPDATE)
    @PutMapping("/updatePwd")
    public ResObject updatePwd(String oldPassword, String newPassword) {
        LoginUserDetails loginUser = getLoginUser();
        String userName = loginUser.getUsername();
        String password = loginUser.getPassword();
        if (!SecurityUtils.matchesPassword(oldPassword, password)) {
            return R.failure("修改密码失败，旧密码错误");
        }
        if (SecurityUtils.matchesPassword(newPassword, password)) {
            return R.failure("新密码不能与旧密码相同");
        }
        if (userService.resetUserPwd(userName, SecurityUtils.encryptPassword(newPassword))) {
            // 更新缓存用户密码
            loginUser.getUserVo().setPassword(SecurityUtils.encryptPassword(newPassword));
            tokenService.setLoginUser(loginUser);
            return R.success();
        }
        return R.failure("修改密码异常，请联系管理员");
    }

    /**
     * 修改头像
     */
    @EventLog(message = "修改头像", businessType = EventLogEnum.UPDATE)
    @PostMapping("/avatar")
    public ResObject avatar(@RequestParam("file") MultipartFile file) throws IOException, MinioException {
        // 文件存储路径  /2020-01-01/43897583617343545473.jpg
        String filename = file.getOriginalFilename();
        int begin = filename.indexOf(".");
        int last = filename.length();
        String ext = filename.substring(begin, last);
        Path path = Paths.get(LocalDate.now().toString(), Instant.now().toEpochMilli() +  ext);

        minioService.upload(path, file.getInputStream(), file.getContentType());

        String avatarPath = "/" + minioConfig.getBucket() + "/" + path;

        LoginUserDetails loginUser = getLoginUser();
        // 更新缓存用户头像
        loginUser.getUserVo().setAvatar(avatarPath);
        tokenService.setLoginUser(loginUser);

        // 更新数据库数据
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(loginUser.getUserVo().getUserId());
        userEntity.setAvatar(avatarPath);
        userService.updateById(userEntity);

        Map result = new HashMap();
        result.put("fileName", file.getOriginalFilename());
        result.put("filePath", avatarPath);
        return R.success(result);
    }

}
