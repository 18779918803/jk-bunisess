package com.jk.gck.mapper;

import com.jk.common.base.BaseMapper;
import com.jk.gck.entity.LoanReturn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 归还本金和利息映射层
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年05月13日
 */
@Mapper
public interface LoanReturnMapper extends BaseMapper<LoanReturn> {
    List<LoanReturn> getAmountById(@Param("loanId") Integer loanId);
}
