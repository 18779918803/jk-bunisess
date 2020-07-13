package com.jk.gck.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.common.base.IBaseService;
import com.jk.gck.entity.Loan;
import com.jk.sys.entity.User;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 资金划拨服务接口层
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年05月13日
 */
public interface ILoanService extends IBaseService<Loan> {

    IPage selectDebtorAll( Map para);

    IPage selectLeaderAll(Map para);

    List selectDebtorAllList(Map para);

    List selectLeaderAllList(Map para);

    boolean  addCommit(Loan loan);

    boolean addCheck(Loan loan);

    IPage selectLeaderAndDebtorAll(Map para);

    IPage selectDebtorAndLeaderAll(Map para);

    ProcessInstance submitApply(Loan loan, String applyUserId, String key, Map<String, Object> variables);


    List selectLeaderAndDebtorAllList(Map para);

    List selectDebtorAndLeaderAllList(Map para);


    Map selectByLoanId(Integer id);


    List selectLoanList(String name);

    Map selectLoanMapById(Integer id);

    List selectLoansList(Integer page);

    List<User> getUserByRoleAndOrgan(@Param("code") String code, @Param("organId")Integer organId);
}
