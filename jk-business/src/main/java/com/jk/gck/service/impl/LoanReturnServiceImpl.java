package com.jk.gck.service.impl;

import com.jk.common.base.BaseServiceImpl;
import com.jk.gck.entity.Loan;
import com.jk.gck.entity.LoanReturn;
import com.jk.gck.mapper.LoanReturnMapper;
import com.jk.gck.service.ILoanReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 归还本金和利息服务接口实现层
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年05月13日
 */
@Service
public class LoanReturnServiceImpl extends BaseServiceImpl<LoanReturnMapper, LoanReturn> implements ILoanReturnService {

    @Autowired
    private LoanReturnMapper loanReturnMapper;
    @Override
    public List<LoanReturn> getAmountById(Integer loanId) {
        return loanReturnMapper.getAmountById(loanId);
    }
}
