package com.jk.gck.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
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
 * 审批款实体类
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年01月14日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("t_financial_approval")
@ExcelTarget("FinancialApproval")
public class FinancialApproval extends BaseEntity {

    private Integer id;
    /**
     * 审批编号
     */
    @Excel(name = "审批编号")
    private String number;

    /**
     * 审批金额
     */
    @Excel(name = "审批金额")
    private BigDecimal amount;

    /**
     * 审批时间
     */
    @Excel(name = "审批时间", format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

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
     * 审批状态
     */
    @Excel(name = "审批状态")
    private String status;


    @TableField(exist = false)
    private String approvalDes;


    private String attachmentUrl;


    public String getApprovalDes() {
        return approvalDes;
    }

    public void setApprovalDes(String approvalDes) {
        this.approvalDes = approvalDes;
    }

    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractid) {
        this.contractId = contractid;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }
}