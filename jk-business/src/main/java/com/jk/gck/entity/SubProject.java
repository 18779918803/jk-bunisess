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
 * 子项目实体类
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年01月10日
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("t_sub_project")
@ExcelTarget("SubProject")
public class SubProject extends BaseEntity {

    private Integer  id;

    /**
     * 子项目编号
     */
    @Excel(name = "子项目编号")
    private String number;

    /**
     * 子项目名称
     */
    @Excel(name = "子项目名称")
    private String name;

    /**
     * 主项目id
     */
    @Excel(name = "主项目id")
    private Long mainpid;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private String status;

    /**
     * 组织机构
     */
    @Excel(name = "组织机构")
    private Long owner;

    /**
     * 子项目类型
     */
    @Excel(name = "子项目类型")
    private String type;

    /**
     * 描述
     */
    @Excel(name = "描述")
    private String des;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String note;

    @TableField(exist = false)
    private String approvalDes;


    public String getApprovalDes() {
        return approvalDes;
    }

    public void setApprovalDes(String approvalDes) {
        this.approvalDes = approvalDes;
    }

    public Long getMainpid() {
        return mainpid;
    }

    public void setMainpid(Long mainpid) {
        this.mainpid = mainpid;
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