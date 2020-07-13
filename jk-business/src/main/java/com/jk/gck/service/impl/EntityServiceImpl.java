package com.jk.gck.service.impl;

import com.jk.common.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.jk.gck.entity.Entity;
import com.jk.gck.mapper.EntityMapper;
import com.jk.gck.service.IEntityService;



/**
 * 组织服务接口实现层
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年01月10日
 */
@Service
public class EntityServiceImpl extends BaseServiceImpl<EntityMapper, Entity> implements IEntityService {

}
