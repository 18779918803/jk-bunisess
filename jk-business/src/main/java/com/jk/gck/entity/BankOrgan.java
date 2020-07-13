package com.jk.gck.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.jk.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 公司开户信息实体类
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年06月15日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("jk_bank_organ")
public class BankOrgan extends BaseEntity {

    /**
     * 公司名称
     */
    private Integer organId;

    /**
     * 户名
     */
    private String userName;

    /**
     * 开户行
     */
    private String bankName;

    /**
     * 开户账号
     */
    private String bankAccount;

}