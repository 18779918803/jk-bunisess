package com.jk.gck.service.impl;


import com.jk.common.base.BaseServiceImpl;
import com.jk.gck.entity.OpenUser;
import com.jk.gck.mapper.OpenUserMapper;
import com.jk.gck.service.IOpenUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 小程序用户服务接口实现层
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2020年03月06日
 */
@Service
public class OpenUserServiceImpl extends BaseServiceImpl<OpenUserMapper, OpenUser> implements IOpenUserService {

    @Autowired
    private OpenUserMapper openUserMapper;

    @Override
    public void insertOpenUser(OpenUser openUser) {
        openUserMapper.insertOpenUser(openUser);
    }

    @Override
    public OpenUser selectByThirdSession(String thirdSession) {
        return openUserMapper.selectByThirdSession(thirdSession);
    }

    @Override
    public OpenUser selectByOpenid(String openid) {
        return openUserMapper.selectByOpenid(openid);
    }
}
