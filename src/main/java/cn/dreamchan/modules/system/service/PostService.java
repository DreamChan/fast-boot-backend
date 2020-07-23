package cn.dreamchan.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.dreamchan.modules.system.pojo.entity.PostEntity;

import java.util.List;

/**
 *
 * 岗位信息 服务类
 *
 * @author DreamChan
 */
public interface PostService extends IService<PostEntity>{

    List<Integer> selectPostListByUserId(Long userId);

}

