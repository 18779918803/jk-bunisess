package com.jk.gck.service.impl;

import com.jk.common.base.BaseServiceImpl;
import com.jk.gck.entity.ApprovalRecord;
import com.jk.gck.entity.Contract;
import com.jk.gck.entity.Payment;
import com.jk.gck.mapper.ApprovalRecordMapper;
import com.jk.gck.mapper.ContractMapper;
import com.jk.gck.mapper.PaymentMapper;
import com.jk.gck.service.IPaymentService;
import com.jk.gck.utils.ConstUtils;
import com.jk.sys.entity.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 付款服务接口实现层
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年01月14日
 */
@Service
public class PaymentServiceImpl extends BaseServiceImpl<PaymentMapper, Payment> implements IPaymentService {


    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private ApprovalRecordMapper approvalRecordMapper;

    @Autowired
    private ContractMapper contractMapper;

    @Override
    public boolean addCommit(Payment payment) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String username = user.getUsername();
        int row = paymentMapper.updateById(payment);
        if (row > 0) {
            ApprovalRecord approvalRecord = new ApprovalRecord();
            approvalRecord.setApprovalId(Long.valueOf(payment.getId()));
            approvalRecord.setApprovalStatus(payment.getStatus());
            approvalRecord.setName(username);
            approvalRecord.setApprovalTime(new Date());
            approvalRecord.setType(ConstUtils.PAYMENTAPPROVALRECORDTYPE);
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

    @Transactional
    @Override
    public boolean addCheck(Payment payment) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String username = user.getUsername();
        int row = paymentMapper.updateById(payment);
        if (row > 0) {
            ApprovalRecord approvalRecord = new ApprovalRecord();
            approvalRecord.setApprovalId(Long.valueOf(payment.getId()));
            approvalRecord.setApprovalStatus(payment.getStatus());
            approvalRecord.setName(username);
            approvalRecord.setApprovalTime(new Date());
            approvalRecord.setType(ConstUtils.PAYMENTAPPROVALRECORDTYPE);
            approvalRecord.setApprovalDes(payment.getApprovalDes());
            row = approvalRecordMapper.insert(approvalRecord);
            //审核成功，我们更新合同的付款记录
            if (payment.getStatus().equals(ConstUtils.APPROVALSUCCESS)) {  //表示的是审核成功
                Payment findPayment = paymentMapper.selectById(payment.getId());
                Contract contract = contractMapper.selectContractId(findPayment.getContractId());
                Map map = contractMapper.selectAmountByContractId(contract.getId());
                BigDecimal paySum = (BigDecimal) map.get("paySum");
                BigDecimal approvalSum = (BigDecimal) map.get("approvalSum");
                if(paySum.compareTo(approvalSum)>0){
                    contract.setIsWarn(2);
                }else{
                    contract.setIsWarn(1);
                }
                // contract.existWarning();
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
