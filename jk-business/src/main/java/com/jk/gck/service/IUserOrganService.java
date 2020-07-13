package com.jk.gck.service;

import com.jk.common.base.IBaseService;
import com.jk.gck.entity.UserOrgan;

import java.util.List;


/**
 * 用户关联组织服务接口层
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年06月11日
 */
public interface IUserOrganService extends IBaseService<UserOrgan> {

    List<String> getUserOrganbyOrgan(Integer  organId);
}
