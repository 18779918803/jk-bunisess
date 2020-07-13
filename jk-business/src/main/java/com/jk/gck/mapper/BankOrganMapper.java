package com.jk.gck.mapper;


import com.jk.common.base.BaseMapper;
import com.jk.gck.entity.BankOrgan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 公司开户信息映射层
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年06月15日
 */
@Mapper
public interface BankOrganMapper extends BaseMapper<BankOrgan> {
    List<BankOrgan> selectListByOrganId(@Param("organId") Integer organId);
}
