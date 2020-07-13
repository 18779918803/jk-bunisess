package com.jk.gck.entity;


import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jk.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 资金划拨实体类
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年05月13日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("t_loan")
@ExcelTarget("Loan")
public class Loan extends BaseEntity {


    private Integer id;

    @TableField(exist = false)
    private String approvalDes;


    private String number;

    /**
     * 资金调入公司
     */
    @Excel(name = "资金调入公司")
    private Integer debtorId;

    /**
     * 资金调出公司
     */
    @Excel(name = "资金调出公司")
    private Integer leaderId;

    /**
     * 代款金额
     */
    @Excel(name = "代款金额")
    private BigDecimal amount;

    /**
     * 计息时间
     */
    @Excel(name = "计息时间", format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date tradeTime;

    /**
     * 利息
     */
    @Excel(name = "利息")
    private BigDecimal rate;

    /**
     * 应归还时间
     */
    @Excel(name = "应归还时间", format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date returnTime;

    /**
     * 是否还完
     */
    @ExcelIgnore
    private String state;

    /**
     * 审核状态
     */
    @ExcelIgnore
    private String status;

    /**
     * 备注
     */
    @ExcelIgnore
    private String remark;


    /**
     * 申请人
     */
    private String applyUser;

    /**
     * 申请时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date applyTime;


    /**
     * 流程实例ID
     */
    private String instanceId;



    private String createBy;



    private Integer debtorBankId;

    private Integer leaderBankId;


    public Integer getDebtorId() {
        return debtorId;
    }

    public void setDebtorId(Integer debtorId) {
        this.debtorId = debtorId;
    }

    public Integer getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Integer leaderId) {
        this.leaderId = leaderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getApprovalDes() {
        return approvalDes;
    }

    public void setApprovalDes(String approvalDes) {
        this.approvalDes = approvalDes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }


    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }


    public Integer getDebtorBankId() {
        return debtorBankId;
    }

    public void setDebtorBankId(Integer debtorBankId) {
        this.debtorBankId = debtorBankId;
    }

    public Integer getLeaderBankId() {
        return leaderBankId;
    }

    public void setLeaderBankId(Integer leaderBankId) {
        this.leaderBankId = leaderBankId;
    }
}