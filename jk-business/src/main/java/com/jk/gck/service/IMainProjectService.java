package com.jk.gck.service;

import com.jk.common.base.IBaseService;
import com.jk.gck.entity.MainProject;



/**
 * 主项目服务接口层
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年01月10日
 */
public interface IMainProjectService extends IBaseService<MainProject> {

    boolean  addCommit(MainProject mainProject);

    boolean addCheck(MainProject mainProject);
}
