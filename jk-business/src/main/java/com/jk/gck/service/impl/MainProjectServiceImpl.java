package com.jk.gck.service.impl;

import com.jk.common.base.BaseServiceImpl;
import com.jk.gck.entity.ApprovalRecord;
import com.jk.gck.entity.MainProject;
import com.jk.gck.mapper.ApprovalRecordMapper;
import com.jk.gck.mapper.MainProjectMapper;
import com.jk.gck.service.IMainProjectService;
import com.jk.gck.utils.ConstUtils;
import com.jk.sys.entity.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


/**
 * 主项目服务接口实现层
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年01月10日
 */
@Service
@Transactional
public class MainProjectServiceImpl extends BaseServiceImpl<MainProjectMapper, MainProject> implements IMainProjectService {

    @Autowired
    private MainProjectMapper mainProjectMapper;

    @Autowired
    private ApprovalRecordMapper approvalRecordMapper;


    @Override
    public boolean addCommit(MainProject mainProject) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String username = user.getUsername();
        int row = mainProjectMapper.updateById(mainProject);
        if (row > 0) {
            ApprovalRecord approvalRecord = new ApprovalRecord();
            approvalRecord.setApprovalId(Long.valueOf( mainProject.getId()));
            approvalRecord.setApprovalStatus(mainProject.getStatus());
            approvalRecord.setName(username);
            approvalRecord.setApprovalTime(new Date());
            approvalRecord.setType(ConstUtils.MAINPROJECTAPPROVALRECORDTYPE);
            row = approvalRecordMapper.insert(approvalRecord);
            if (row > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    @Override
    public boolean addCheck(MainProject mainProject) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String username = user.getUsername();
        int row = mainProjectMapper.updateById(mainProject);
        if (row > 0) {
            ApprovalRecord approvalRecord = new ApprovalRecord();
            approvalRecord.setApprovalId(Long.valueOf( mainProject.getId()));
            approvalRecord.setApprovalStatus(mainProject.getStatus());
            approvalRecord.setName(username);
            approvalRecord.setApprovalTime(new Date());
            approvalRecord.setType(ConstUtils.MAINPROJECTAPPROVALRECORDTYPE);
            approvalRecord.setApprovalDes(mainProject.getApprovalDes());
            row = approvalRecordMapper.insert(approvalRecord);
            if (row > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
