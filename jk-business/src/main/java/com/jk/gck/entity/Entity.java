package com.jk.gck.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jk.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 组织实体类
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年01月10日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("t_entity")
@ExcelTarget("Entity")
public class Entity extends BaseEntity {

    /**
     * 组织名称
     */
    @Excel(name="组织名称")
    private String name;

    /**
     * 编号
     */
    @Excel(name="编号")
    private String creditCode;

    /**
     * 地址
     */
    @Excel(name="地址")
    private String address;

    /**
     * 银行账户
     */
    @Excel(name="银行账户")
    private String bankAccount;

    /**
     * 银行账户
     */
    @Excel(name="开户行")
    private String bankName;

    /**
     * 备注
     */
    @Excel(name="备注")
    private String note;

    /**
     * 是否是内部组织
     */
    @Excel(name="是否是内部组织")
    private String isInternal;

    /**
     * 状态
     */
    @Excel(name="状态")
    private String status;


    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }
}