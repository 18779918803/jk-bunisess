package com.jk.gck.service;

import com.jk.common.base.IBaseService;
import com.jk.gck.entity.SubProject;

import java.util.List;


/**
 * 子项目服务接口层
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年01月10日
 */
public interface ISubProjectService extends IBaseService<SubProject> {


    List<SubProject> selectByMainProjectId(Long mainProjectId);

    boolean addCommit(SubProject subProject);

    boolean addCheck(SubProject subProject);
}
