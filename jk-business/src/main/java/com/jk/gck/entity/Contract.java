package com.jk.gck.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jk.common.base.BaseEntity;
import com.jk.gck.utils.ConstUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 合同实体类
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年01月13日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("t_contract")
@ExcelTarget("Contract")
public class Contract extends BaseEntity {

    public static final String start_state = "1"; //开工
    public static final String done_state = "2"; //完工
    public static final String check_state = "3"; //竣工验收
    public static final String audit_state = "4"; //结算
    public static final String quality_out_state = "5"; //出质保期

    private Integer id;
    /**
     * 合同编号
     */
    @Excel(name = "合同编号")
    private String number;

    /**
     * 合同类型
     */
    @Excel(name = "合同类型")
    private String type;

    /**
     * 中标价
     */
    @Excel(name = "中标价")
    private BigDecimal price;

    /**
     * 验收付款比例
     */
    @Excel(name = "验收付款比例")
    private BigDecimal payrate;

    /**
     * 完工付款比例
     */
    @Excel(name = "完工付款比例")
    private BigDecimal donepayrate;

    /**
     * 合同状态
     */
    @Excel(name = "合同状态")
    private String state;

    /**
     * 审批金额
     */
    @ExcelIgnore
    @TableField(exist = false)
    private BigDecimal approvalsum;

    /**
     * 付款金额
     */
    @ExcelIgnore
    @TableField(exist = false)
    private BigDecimal paysum;

    /**
     * 发票金额
     */
    @ExcelIgnore
    @TableField(exist = false)
    private BigDecimal billsum;

    /**
     * 质保金比例
     */
    @Excel(name = "质保金比例")
    private BigDecimal qualitydepositrate;

    /**
     * 质保期
     */
    @Excel(name = "质保期")
    private Integer qualityperiod;

    /**
     * 竣工时间
     */
    @Excel(name = "竣工时间", format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date completiondate;

    /**
     * 质保期维修费
     */
    @Excel(name = "质保期维修费")
    private BigDecimal qualityfixpay;

    /**
     * 结算价
     */
    @Excel(name = "结算价")
    private BigDecimal auditprice;

    /**
     * 最终结算价
     */
    @Excel(name = "最终结算价")
    private BigDecimal finalauditprice;

    /**
     * 子项目
     */
    @Excel(name = "子项目")
    private Long projectid;

    /**
     * 甲方
     */
    @Excel(name = "甲方")
    private Long partya;

    /**
     * 乙方
     */
    @Excel(name = "乙方")
    private Long partyb;

    /**
     * 合同签订时间
     */
    @Excel(name = "合同签订时间", format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date signdate;

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

    /**
     * 监理中标费率
     */
    @Excel(name = "监理中标费率")
    private BigDecimal supervisionrate;

    /**
     * 检测中标费率
     */
    @Excel(name = "检测中标费率")
    private BigDecimal checkrate;

    /**
     * 警告信息
     */
    @ExcelIgnore
    private String warning;


    private  Integer  isWarn;

    /**
     * 乙方子公司
     */
    @Excel(name = "乙方子公司")
    private Long partyc;

    /**
     * 主项目
     */
    @Excel(name = "主项目")
    private Long mainprojectid;

    /**
     * 合同名称
     */
    @Excel(name = "合同名称")
    private String contractname;


    @TableField(exist = false)
    private String approvalDes;


    public Long getMainprojectid() {
        return mainprojectid;
    }

    public void setMainprojectid(Long mainprojectid) {
        this.mainprojectid = mainprojectid;
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

    public String getApprovalDes() {
        return approvalDes;
    }

    public void setApprovalDes(String approvalDes) {
        this.approvalDes = approvalDes;
    }


    public BigDecimal getApprovalsum() {
        return approvalsum;
    }

    public void setApprovalsum(BigDecimal approvalsum) {
        this.approvalsum = approvalsum;
    }


    public BigDecimal getPaysum() {
        return paysum;
    }

    public void setPaysum(BigDecimal paysum) {
        this.paysum = paysum;
    }


    public boolean existWarning() {
        /*if (TextUtils.isNotEmpty(this.warning)) {
            return true;
        }*/
        //状态缺失可能是历史数据, 不生成警告信息
        if (state == null) {
            return false;
        }
        //检测生成警告信息
        String warnings = "";

        if (paysum == null) {
            paysum = BigDecimal.ZERO;
        }
        if (approvalsum == null) {
            approvalsum = BigDecimal.ZERO;
        }
        if (ConstUtils.CONSTRUCTION.equals(type) || ConstUtils.MATCHING.equals(type)) {   //表示的是施工和配套合同
            switch (state) {
                case quality_out_state: {
                    //# 出质保态（退质保金） 累计已支付  <=  结算价
                    //二次抽审价不为空以抽审价为限
                    if (finalauditprice != null && !finalauditprice.equals(BigDecimal.ZERO)) {
                        if (paysum.compareTo(finalauditprice) > 0) {
                            warnings += "[已出质保]累计支付超出(抽审价);";
                        }
                        if (qualityfixpay.compareTo(finalauditprice.multiply(qualitydepositrate).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP)) > 0) {
                            warnings += "[已出质保]维修费超出(质保金);";
                        }
                        if (paysum.compareTo(approvalsum) > 0) {
                            warnings += "[已出质保]累计支付超出(累计财政审批);";
                        }
                    } else {
                        if (paysum.compareTo(auditprice) > 0) {
                            warnings += "[已出质保]累计支付超出(结算价);";
                        }
                        if (qualityfixpay.compareTo(auditprice.multiply(qualitydepositrate).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP)) > 0) {
                            warnings += "[已出质保]维修费超出(质保金);";
                        }
                        if (paysum.compareTo(approvalsum) > 0) {
                            warnings += "[已出质保]累计支付超出(累计财政审批);";
                        }
                    }
                    break;
                }

                //结算状态
                case audit_state: {

                    if (finalauditprice != null && !finalauditprice.equals(BigDecimal.ZERO)) {

                        if (qualitydepositrate != null) {  //质保金比例
                            if (qualityfixpay.compareTo(finalauditprice.multiply(qualitydepositrate.divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP))) > 0) {
                                warnings += "[已结算]维修费超出(质保金);";
                            }
                            if (paysum.compareTo(finalauditprice.multiply(new BigDecimal("100").subtract(qualitydepositrate).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP))) > 0) {
                                warnings += "[已结算]累计支付超出(抽审价减去质保金);";
                            }
                        } else {
                            if (paysum.compareTo(finalauditprice) > 0) {
                                warnings += "[已结算]累计支付超出(抽审价);";
                            }
                        }
                        if (paysum.compareTo(approvalsum) > 0) {
                            warnings += "[已结算]累计支付超出(累计财政审批);";
                        }

                    } else {

                        if (qualitydepositrate != null) {  //质保金比例
                            if (paysum.compareTo(auditprice.multiply(new BigDecimal("100").subtract(qualitydepositrate).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP))) > 0) {
                                warnings += "[已结算]累计支付超出(结算价减去质保金);";
                            }

                            if (qualityfixpay.compareTo(auditprice.multiply(qualitydepositrate.divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP))) > 0) {
                                warnings += "[已结算]维修费超出(质保金);";
                            }
                        } else {
                            if (paysum != null && auditprice != null && paysum.compareTo(auditprice) > 0) {
                                warnings += "[已结算]累计支付超出(结算价);";
                            }
                        }
                        if (paysum.compareTo(approvalsum) > 0) {
                            warnings += "[已结算]累计支付超出(累计财政审批);";
                        }

                    }
                    break;
                }

                //竣工验收状态
                case check_state: {

                    if (paysum.compareTo(approvalsum) > 0) {
                        warnings += "[已验收]累计支付超出(累计财政审批);";
                    }
                    if (qualitydepositrate != null) {  //质保金比例
                        if (qualityfixpay.compareTo(price.multiply(qualitydepositrate.divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP))) > 0) {
                            warnings += "[已验收]维修费超出(质保金);";
                        }
                    }

                    //如果累计支付超过竣工验收比例
                    if (payrate != null) {
                        if (paysum.compareTo(price.multiply(payrate.divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP))) > 0) {
                            warnings += "[已验收]累计支付超出(完工竣工验收比例);";
                        }
                    }
                    break;
                }
                case done_state: {   // 完工状态

                    if (paysum.compareTo(approvalsum) > 0) {
                        warnings += "[已完工]累计支付超出(累计财政审批);";
                    }
                    //如果累计支付超过完工验收比例
                    if (donepayrate != null) {
                        if (paysum.compareTo(price.multiply(donepayrate.divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP))) > 0) {
                            warnings += "[已完工]累计支付超出(完工付款比例);";
                        }
                    }
                    break;
                }
                case start_state: {   //开工状态

                    if (paysum.compareTo(approvalsum) > 0) {
                        warnings += "[已开工]累计支付超出(累计财政审批);";
                    }

                    //如果累计支付大于完工比例
                    if (donepayrate != null) {
                        if (paysum.compareTo(price.multiply(donepayrate.divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP))) > 0) {
                            warnings += "[已开工]累计支付超出(完工付款比例);";
                        }
                    }

                    break;
                }
            }
        } else if (ConstUtils.SUPERVISION.equals(type)) {   // 监理合同
            BigDecimal supervisionPrize = BigDecimal.ZERO;  //监理费= 中标价*监理中标比例。
            if (supervisionrate != null) {
                supervisionPrize = price.multiply(supervisionrate.divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP));
            }
            switch (state) {

                //结算状态
                case audit_state: {

                    if (finalauditprice != null && !finalauditprice.equals(BigDecimal.ZERO)) {


                        if (paysum.compareTo(finalauditprice) > 0) {
                            warnings += "[已结算]累计支付超出(抽审价);";
                        }

                        if (paysum.compareTo(approvalsum) > 0) {
                            warnings += "[已结算]累计支付超出(累计财政审批);";
                        }

                    } else {

                        if (auditprice != null && paysum.compareTo(auditprice) > 0) {
                            warnings += "[已结算]累计支付超出(结算价);";
                        }

                        if (paysum.compareTo(approvalsum) > 0) {
                            warnings += "[已结算]累计支付超出(累计财政审批);";
                        }

                    }
                    break;
                }

                //竣工验收状态
                case check_state: {

                    if (paysum.compareTo(approvalsum) > 0) {
                        warnings += "[已验收]累计支付超出(累计财政审批);";
                    }

                    //如果累计支付超过竣工验收比例
                    if (payrate != null) {
                        if (paysum.compareTo(supervisionPrize.multiply(payrate.divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP))) > 0) {
                            warnings += "[已验收]累计支付超出(完工竣工验收比例);";
                        }
                    }
                    break;
                }
                case done_state: {   // 完工状态

                    if (paysum.compareTo(approvalsum) > 0) {
                        warnings += "[已完工]累计支付超出(累计财政审批);";
                    }
                    //如果累计支付超过完工验收比例
                    if (donepayrate != null) {
                        if (paysum.compareTo(supervisionPrize.multiply(donepayrate.divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP))) > 0) {
                            warnings += "[已完工]累计支付超出(完工付款比例);";
                        }
                    }
                    break;
                }
                case start_state: {   //开工状态

                    if (paysum.compareTo(approvalsum) > 0) {
                        warnings += "[已开工]累计支付超出(累计财政审批);";
                    }

                    //如果累计支付大于完工比例
                    if (supervisionrate != null) {
                        if (paysum.compareTo(supervisionPrize.multiply(donepayrate.divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP))) > 0) {
                            warnings += "[已开工]累计支付超出(完工付款比例);";
                        }
                    }

                    break;
                }
            }

        } else if (ConstUtils.CHECK.equals(type)) {   //检测合同

            BigDecimal checkPrize = BigDecimal.ZERO;  //监理费= 中标价*监理中标比例。
            if (supervisionrate != null) {
                checkPrize = price.multiply(checkrate.divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP));
            }
            switch (state) {

                //结算状态
                case audit_state: {

                    if (finalauditprice != null && !finalauditprice.equals(BigDecimal.ZERO)) {


                        if (paysum.compareTo(finalauditprice) > 0) {
                            warnings += "[已结算]累计支付超出(抽审价);";
                        }

                        if (paysum.compareTo(approvalsum) > 0) {
                            warnings += "[已结算]累计支付超出(累计财政审批);";
                        }

                    } else {

                        if (paysum.compareTo(auditprice) > 0) {
                            warnings += "[已结算]累计支付超出(结算价);";
                        }

                        if (paysum.compareTo(approvalsum) > 0) {
                            warnings += "[已结算]累计支付超出(累计财政审批);";
                        }

                    }
                    break;
                }

                //竣工验收状态
                case check_state: {

                    if (paysum.compareTo(approvalsum) > 0) {
                        warnings += "[已验收]累计支付超出(累计财政审批);";
                    }

                    //如果累计支付超过竣工验收比例
                    if (payrate != null) {
                        if (paysum.compareTo(checkPrize.multiply(payrate.divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP))) > 0) {
                            warnings += "[已验收]累计支付超出(完工竣工验收比例);";
                        }
                    }
                    break;
                }
                case done_state: {   // 完工状态

                    if (paysum.compareTo(approvalsum) > 0) {
                        warnings += "[已完工]累计支付超出(累计财政审批);";
                    }
                    //如果累计支付超过完工验收比例
                    if (donepayrate != null) {
                        if (paysum.compareTo(checkPrize.multiply(donepayrate.divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP))) > 0) {
                            warnings += "[已完工]累计支付超出(完工付款比例);";
                        }
                    }
                    break;
                }
                case start_state: {   //开工状态

                    if (paysum.compareTo(approvalsum) > 0) {
                        warnings += "[已开工]累计支付超出(累计财政审批);";
                    }

                    //如果累计支付大于完工比例
                    if (supervisionrate != null) {
                        if (paysum.compareTo(checkPrize.multiply(donepayrate.divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP))) > 0) {
                            warnings += "[已开工]累计支付超出(完工付款比例);";
                        }
                    }

                    break;
                }
            }

        } else {   //其余的合同
            if (paysum.compareTo(approvalsum) > 0) {
                warnings += "[已完工]累计支付超出(累计财政审批);";
            }

            if (auditprice != null && paysum.compareTo(auditprice) > 0) {
                warnings += "[已结算]累计支付超出(结算价);";
            }
        }
        boolean existWarning;

        if (warnings != null && !warnings.trim().equals("")) {
            existWarning = true;
        } else {
            existWarning = false;
        }
        if (existWarning) {
            this.warning = warnings;
        } else {
            this.warning = "";
        }
        return existWarning;
    }


    public BigDecimal getBillsum() {
        return billsum;
    }

    public void setBillsum(BigDecimal billsum) {
        this.billsum = billsum;
    }


    public Integer getIsWarn() {
        return isWarn;
    }

    public void setIsWarn(Integer isWarn) {
        this.isWarn = isWarn;
    }
}