package com.jk.gck.mapper;


import com.jk.common.base.BaseMapper;
import com.jk.gck.entity.Loan;
import com.jk.sys.entity.Role;
import com.jk.sys.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 资金划拨映射层
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年05月13日
 */
@Mapper
public interface LoanMapper extends BaseMapper<Loan> {

    List selectDebtorAll(Map para);

    List selectLeaderAll(Map para);

    List selectLeaderAndDebtorAll(Map para);

    List selectDebtorAndLeaderAll(Map para);

    Map selectByLoanId(@Param("id") Integer id);

    Map selectLoanMapById(@Param("id") Integer id);

    List selectByLoanState(@Param("status") String status);

    Integer selectUserIdByName(@Param("username")String username);

    /**
     * 获取用户角色
     *
     * @param userId 用户id
     * @return {@link List<  Role  >}
     */
    List<Role> selectUserRoles(@Param("userId") Integer userId);

    List selectByLoan(@Param("start")int start);

    //借款方的
    List<User> getUserByRoleAndOrgan(@Param("code") String code, @Param("organId")Integer organId);
}
