package com.jk.gck.entity;

import java.io.Serializable;

public class Count implements Serializable {


    private Integer contractCount;
    private Integer entityCount;
    private Integer financialApprovalCount;
    private Integer paymentCount;
    private Integer mainProjectCount;
    private Integer subProjectCount;


    public Integer getContractCount() {
        return contractCount;
    }

    public void setContractCount(Integer contractCount) {
        this.contractCount = contractCount;
    }

    public Integer getEntityCount() {
        return entityCount;
    }

    public void setEntityCount(Integer entityCount) {
        this.entityCount = entityCount;
    }

    public Integer getFinancialApprovalCount() {
        return financialApprovalCount;
    }

    public void setFinancialApprovalCount(Integer financialApprovalCount) {
        this.financialApprovalCount = financialApprovalCount;
    }

    public Integer getPaymentCount() {
        return paymentCount;
    }

    public void setPaymentCount(Integer paymentCount) {
        this.paymentCount = paymentCount;
    }

    public Integer getMainProjectCount() {
        return mainProjectCount;
    }

    public void setMainProjectCount(Integer mainProjectCount) {
        this.mainProjectCount = mainProjectCount;
    }

    public Integer getSubProjectCount() {
        return subProjectCount;
    }

    public void setSubProjectCount(Integer subProjectCount) {
        this.subProjectCount = subProjectCount;
    }
}
