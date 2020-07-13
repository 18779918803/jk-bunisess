package com.jk.gck.service;

import com.jk.common.base.IBaseService;
import com.jk.gck.entity.FinancialApproval;


/**
 * 审批款服务接口层
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年01月14日
 */
public interface IFinancialApprovalService extends IBaseService<FinancialApproval> {

    boolean addCommit(FinancialApproval financialApproval);

    boolean addCheck(FinancialApproval financialApproval);
}
