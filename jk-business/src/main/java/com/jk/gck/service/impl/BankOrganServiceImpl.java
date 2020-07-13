package com.jk.gck.service.impl;

import com.jk.common.base.BaseServiceImpl;
import com.jk.gck.entity.BankOrgan;
import com.jk.gck.mapper.BankOrganMapper;
import com.jk.gck.service.IBankOrganService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 公司开户信息服务接口实现层
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年06月15日
 */
@Service
public class BankOrganServiceImpl extends BaseServiceImpl<BankOrganMapper, BankOrgan> implements IBankOrganService {

    @Autowired
    private BankOrganMapper  bankOrganMapper;

    @Override
    public List<BankOrgan> selectListByOrganId(Integer organId) {
        return bankOrganMapper.selectListByOrganId(organId);
    }
}
