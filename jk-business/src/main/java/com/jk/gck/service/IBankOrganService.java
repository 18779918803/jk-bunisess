package com.jk.gck.service;

import com.jk.common.base.IBaseService;
import com.jk.gck.entity.BankOrgan;

import java.util.List;


/**
 * 公司开户信息服务接口层
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年06月15日
 */
public interface IBankOrganService extends IBaseService<BankOrgan> {

    List<BankOrgan> selectListByOrganId(Integer organId);
}
