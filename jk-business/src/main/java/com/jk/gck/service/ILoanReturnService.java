package com.jk.gck.service;

import com.jk.common.base.IBaseService;
import com.jk.gck.entity.LoanReturn;

import java.util.List;


/**
 * 归还本金和利息服务接口层
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年05月13日
 */
public interface ILoanReturnService extends IBaseService<LoanReturn> {
    List<LoanReturn> getAmountById(Integer loanId);
}
