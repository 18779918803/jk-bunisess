package com.jk.gck.mapper;


import com.jk.common.base.BaseMapper;
import com.jk.gck.entity.UserOrgan;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户关联组织映射层
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年06月11日
 */
@Mapper
public interface UserOrganMapper extends BaseMapper<UserOrgan> {
    List<String> getUserOrganbyOrgan(Integer organId);
}
