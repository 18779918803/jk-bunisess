package com.jk.gck.service.impl;

import com.jk.common.base.BaseServiceImpl;
import com.jk.gck.entity.UserOrgan;
import com.jk.gck.mapper.UserOrganMapper;
import com.jk.gck.service.IUserOrganService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 用户关联组织服务接口实现层
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年06月11日
 */
@Service
public class UserOrganServiceImpl extends BaseServiceImpl<UserOrganMapper, UserOrgan> implements IUserOrganService {


    @Autowired
    private UserOrganMapper  userOrganMapper;
    @Override
    public List<String> getUserOrganbyOrgan(Integer organId) {
        return userOrganMapper.getUserOrganbyOrgan(organId);
    }
}
