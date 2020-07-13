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
 * 付款实体类
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年01月14日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("t_payment")
@ExcelTarget("Payment")
public class Payment extends BaseEntity {


    private Integer id;

    /**
     * 付款凭证号
     */
    @Excel(name = "付款凭证号")
    private String number;

    /**
     * 实付
     */
    @Excel(name = " 应付", type = 10)
    private BigDecimal amount;

    /**
     * 应付
     */
    @Excel(name = "实付", type = 10)
    private BigDecimal shouldAmount;

    /**
     * 扣款
     */
    @Excel(name = "扣款")
    private BigDecimal cutAmount;

    /**
     * 发票金额
     */
    @Excel(name = "发票金额")
    private BigDecimal billAmount;

    /**
     * 扣款说明
     */
    @Excel(name = "扣款说明")
    private String cutNote;

    /**
     * 交易时间
     */
    @Excel(name = "交易时间", format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date tradeTime;

    /**
     * 合同
     */
    @Excel(name = "合同")
    private Integer contractId;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String note;

    /**
     * status
     */
    @ExcelIgnore
    private String status;


    @TableField(exist = false)
    private String approvalDes;


    public String getApprovalDes() {
        return approvalDes;
    }

    public void setApprovalDes(String approvalDes) {
        this.approvalDes = approvalDes;
    }


    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


    public BigDecimal getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(BigDecimal billAmount) {
        this.billAmount = billAmount;
    }
}