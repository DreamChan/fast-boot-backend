package cn.dreamchan.modules.cms.service.impl;

import cn.dreamchan.modules.cms.mapper.ArticleMapper;
import cn.dreamchan.modules.cms.service.ArticleService;
import cn.dreamchan.common.base.BaseService;
import cn.dreamchan.modules.cms.pojo.entity.ArticleEntity;

import org.springframework.stereotype.Service;

/**
 * 文章 服务实现类
 *
 * @author DreamChan
 */
@Service
public class ArticleServiceImpl extends BaseService<ArticleMapper, ArticleEntity> implements ArticleService {

}

