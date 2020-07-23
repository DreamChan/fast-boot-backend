package cn.dreamchan.modules.monitor.controller;

import cn.dreamchan.common.base.BaseController;
import cn.dreamchan.common.biz.R;
import cn.dreamchan.common.biz.ResObject;
import cn.dreamchan.common.constant.Constants;
import cn.dreamchan.common.utils.StringUtils;
import cn.dreamchan.component.redis.RedisService;
import cn.dreamchan.component.security.pojo.LoginUserDetails;
import cn.dreamchan.modules.monitor.pojo.vo.OnlineUserVo;
import cn.dreamchan.modules.monitor.service.mapstruct.OnlineUserMapStruct;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 在线用户管理
 *
 * @author DreamChan
 */
@Api(tags = "在线用户管理")
@Slf4j
@RestController
@RequestMapping("/monitor/online")
public class OnlineUserController extends BaseController {

    @Autowired
    private RedisService redisService;
    @Autowired
    private OnlineUserMapStruct onlineUserMapStruct;

    /**
     * 在线用户列表
     * @param userName
     * @return
     */
    @PreAuthorize("hasPermission('/monitor/online',  'monitor:online:query')")
    @GetMapping("/allList")
    public ResObject allList(String userName) {

        Collection<String> keys = redisService.keys(Constants.LOGIN_TOKEN_KEY + "*");
        List<OnlineUserVo> userOnlineList = new ArrayList<>();
        for (String key : keys) {

            LoginUserDetails user = redisService.getObject(key);
            OnlineUserVo onlineUserVo = onlineUserMapStruct.toVo(user);
            onlineUserVo.setUserName(user.getUsername());

            if (StringUtils.isNotBlank(userName)) {
                if (userName.equals(onlineUserVo.getUserName())) {
                    userOnlineList.add(onlineUserVo);
                }
            } else {
                userOnlineList.add(onlineUserVo);
            }
        }
        userOnlineList.removeAll(Collections.singleton(null));

        Map result = new HashMap();
        result.put("records", userOnlineList);
        result.put("total", userOnlineList.size());
        return R.success(result);
    }


    /**
     * 用户强制下线
     */
    @ApiOperation("用户强制下线")
    @ApiImplicitParam(name = "tokenId", required = true, dataType = "Long", paramType = "path")
    @PreAuthorize("hasPermission('/monitor/online',  'monitor:online:logout')")
    @DeleteMapping("/{tokenId}")
    public ResObject forceLogout(@PathVariable String tokenId) {

        redisService.del(Constants.LOGIN_TOKEN_KEY + tokenId);
        return R.success();
    }
}
