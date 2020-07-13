package com.jk.gck.service;


import com.jk.common.base.IBaseService;
import com.jk.gck.entity.OpenUser;

/**
 * 小程序用户服务接口层
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2020年03月06日
 */
public interface IOpenUserService extends IBaseService<OpenUser> {

    void insertOpenUser(OpenUser openUser);

    OpenUser selectByThirdSession(String thirdSession);

    OpenUser selectByOpenid(String openid);
}
