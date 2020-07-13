package com.jk.gck.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.jk.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 归还本金和利息实体类
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年05月13日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("t_loan_return")
public class LoanReturn extends BaseEntity {

    /**
     * 合同
     */
    private Integer loanId;

    /**
     * 还款金额
     */
    private BigDecimal amount;


    /**
     * 表示的是应还利息
     */
    private BigDecimal shouldRate;

    /**
     * 实际还的利息
     */
    private  BigDecimal  rate;


    /**
     * 表示的是应还租金
     */
    private  BigDecimal  shouldAmount;



    /**
     * 还款时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date tradeTime;


    /**
     * 审核状态
     */
    private String status;

    /**
     * 备注
     */
    private String remark;


    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
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


    public BigDecimal getShouldRate() {
        return shouldRate;
    }

    public void setShouldRate(BigDecimal shouldRate) {
        this.shouldRate = shouldRate;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getShouldAmount() {
        return shouldAmount;
    }

    public void setShouldAmount(BigDecimal shouldAmount) {
        this.shouldAmount = shouldAmount;
    }
}