package com.jk.gck.mapper;

import com.jk.common.base.BaseMapper;
import com.jk.gck.entity.SubProject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 子项目映射层
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年01月10日
 */
@Mapper
public interface SubProjectMapper extends BaseMapper<SubProject> {
    List<SubProject> selectByMainProjectId(@Param("mainpid") Long mainpid);
}
