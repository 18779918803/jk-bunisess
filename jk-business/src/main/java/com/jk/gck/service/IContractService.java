package com.jk.gck.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.common.base.IBaseService;
import com.jk.gck.entity.Contract;
import com.jk.gck.entity.Count;

import java.util.List;
import java.util.Map;


/**
 * 合同服务接口层
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年01月13日
 */
public interface IContractService extends IBaseService<Contract> {

    Map<String, Object> selectMapById(Integer contractid);

    IPage selectAllContractByMainProject(Map para);

    IPage selectAllContractBySubProject(Map para);

    Map<String, Object>  selectByContractId(Integer contractId);

    IPage selectAllContractByContract(Map para);

    boolean addCommit(Contract contract);

    boolean addCheck(Contract contract);

    Count getMainCount();

    Long getWarningCount();

    Contract selectContractId(Integer id);

    IPage selectContractByPartya(Map para);

    List selectContractAllByPartya(Map para);

    Map selectAmountByContractId(Integer id);
}
