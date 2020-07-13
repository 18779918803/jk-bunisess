package com.jk.gck.mapper;

import com.jk.common.base.BaseMapper;
import com.jk.gck.entity.Contract;
import com.jk.gck.entity.Count;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 合同映射层
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年01月13日
 */
@Mapper
public interface ContractMapper extends BaseMapper<Contract> {

    Map<String, Object> selectMapById(@Param("contractid") Integer contractid);

    List selectAllContractByMainProject(Map para);
    List selectAllContractBySubProject(Map para);

    List selectAllContractByContract(Map para);

    Count getMainCount();

    Long getWarningCount();

    Map<String, Object> selectByContractId(@Param("id") Integer contractId);

    Contract selectContractId(@Param("id") Integer id);

    List selectContractByPartya(Map para);

    Map selectAmountByContractId(@Param("contractId") Integer id);
}
