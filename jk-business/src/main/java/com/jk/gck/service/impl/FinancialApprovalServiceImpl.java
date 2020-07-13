package com.jk.gck.service.impl;

import com.jk.common.base.BaseServiceImpl;
import com.jk.gck.entity.ApprovalRecord;
import com.jk.gck.entity.Contract;
import com.jk.gck.entity.FinancialApproval;
import com.jk.gck.mapper.ApprovalRecordMapper;
import com.jk.gck.mapper.ContractMapper;
import com.jk.gck.mapper.FinancialApprovalMapper;
import com.jk.gck.service.IFinancialApprovalService;
import com.jk.gck.utils.ConstUtils;
import com.jk.sys.entity.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 审批款服务接口实现层
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年01月14日
 */
@Service
public class FinancialApprovalServiceImpl extends BaseServiceImpl<FinancialApprovalMapper, FinancialApproval> implements IFinancialApprovalService {


    @Autowired
    private FinancialApprovalMapper financialApprovalMapper;

    @Autowired
    private ApprovalRecordMapper approvalRecordMapper;


    @Autowired
    private ContractMapper contractMapper;

    @Override
    public boolean addCommit(FinancialApproval financialApproval) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String username = user.getUsername();
        int row = financialApprovalMapper.updateById(financialApproval);
        if (row > 0) {
            ApprovalRecord approvalRecord = new ApprovalRecord();
            approvalRecord.setApprovalId(Long.valueOf(financialApproval.getId()));
            approvalRecord.setApprovalStatus(financialApproval.getStatus());
            approvalRecord.setName(username);
            approvalRecord.setApprovalTime(new Date());
            approvalRecord.setType(ConstUtils.FINANCIALAPPROVALAPPROVALRECORDTYPE);
            row = approvalRecordMapper.insert(approvalRecord);
            if (row > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean addCheck(FinancialApproval financialApproval) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String username = user.getUsername();
        int row = financialApprovalMapper.updateById(financialApproval);
        if (row > 0) {
            ApprovalRecord approvalRecord = new ApprovalRecord();
            approvalRecord.setApprovalId(Long.valueOf(financialApproval.getId()));
            approvalRecord.setApprovalStatus(financialApproval.getStatus());
            approvalRecord.setName(username);
            approvalRecord.setApprovalTime(new Date());
            approvalRecord.setType(ConstUtils.FINANCIALAPPROVALAPPROVALRECORDTYPE);
            approvalRecord.setApprovalDes(financialApproval.getApprovalDes());
            row = approvalRecordMapper.insert(approvalRecord);
            //审核成功，我们更新合同的付款记录
            if (financialApproval.getStatus().equals(ConstUtils.APPROVALSUCCESS)) {  //表示的是审核成功
                FinancialApproval financialApprovals = financialApprovalMapper.selectById(financialApproval.getId());
                Contract contract = contractMapper.selectById(financialApprovals.getContractId());
                Map map = contractMapper.selectAmountByContractId(contract.getId());
                BigDecimal paySum = (BigDecimal) map.get("paySum");
                BigDecimal approvalSum = (BigDecimal) map.get("approvalSum");

                if (paySum.compareTo(approvalSum) > 0) {
                    contract.setIsWarn(2);
                } else {
                    contract.setIsWarn(1);
                }
                contractMapper.updateById(contract);
            }
            if (row > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
