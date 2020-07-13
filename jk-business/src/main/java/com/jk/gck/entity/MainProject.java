package com.jk.gck.entity;


import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jk.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 主项目实体类
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年01月10日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("t_main_project")
@ExcelTarget("MainProject")
public class MainProject extends BaseEntity {


    /**
     * 主项目的id
     */
    private  Integer id;

    /**
     * 主项目编号
     */
    @Excel(name = "主项目编号")
    private String number;

    /**
     * 主项目名字
     */
    @Excel(name = "主项目名字")
    private String name;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String note;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private String status;


    /**
     * 审批描述
     */
    @TableField(exist = false)
    private String approvalDes;


    public String getApprovalDes() {
        return approvalDes;
    }

    public void setApprovalDes(String approvalDes) {
        this.approvalDes = approvalDes;
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
}