package com.jk.gck.mapper;


import com.jk.common.base.BaseMapper;
import com.jk.gck.entity.OpenUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 小程序用户映射层
 *
 * @author 缪隽峰
 * @version 1.0
 * @date 2020年03月06日
 */
@Mapper
public interface OpenUserMapper extends BaseMapper<OpenUser> {

    void insertOpenUser(OpenUser openUser);

    OpenUser selectByThirdSession(@Param("thirdSession") String thirdSession);

    OpenUser selectByOpenid(@Param("openid") String openid);
}
