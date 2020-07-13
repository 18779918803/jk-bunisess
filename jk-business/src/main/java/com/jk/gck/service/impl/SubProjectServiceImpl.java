package com.jk.gck.service.impl;

import com.jk.common.base.BaseServiceImpl;
import com.jk.gck.entity.ApprovalRecord;
import com.jk.gck.entity.SubProject;
import com.jk.gck.mapper.ApprovalRecordMapper;
import com.jk.gck.mapper.SubProjectMapper;
import com.jk.gck.service.ISubProjectService;
import com.jk.gck.utils.ConstUtils;
import com.jk.sys.entity.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * 子项目服务接口实现层
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年01月10日
 */
@Service
public class SubProjectServiceImpl extends BaseServiceImpl<SubProjectMapper, SubProject> implements ISubProjectService {

    @Autowired
    private SubProjectMapper subProjectMapper;

    @Autowired
    private ApprovalRecordMapper approvalRecordMapper;

    @Override
    public List<SubProject> selectByMainProjectId(Long mainProjectId) {
        return subProjectMapper.selectByMainProjectId(mainProjectId);
    }
    @Transactional
    @Override
    public boolean addCommit(SubProject subProject) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String username = user.getUsername();
        int row = subProjectMapper.updateById(subProject);
        if (row > 0) {
            ApprovalRecord approvalRecord = new ApprovalRecord();
            approvalRecord.setApprovalId(Long.valueOf( subProject.getId()));
            approvalRecord.setApprovalStatus(subProject.getStatus());
            approvalRecord.setName(username);
            approvalRecord.setApprovalTime(new Date());
            approvalRecord.setType(ConstUtils.SUBPROJECTAPPROVALRECORDTYPE);
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

    @Transactional
    @Override
    public boolean addCheck(SubProject subProject) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String username = user.getUsername();
        int row = subProjectMapper.updateById(subProject);
        if (row > 0) {
            ApprovalRecord approvalRecord = new ApprovalRecord();
            approvalRecord.setApprovalId(Long.valueOf( subProject.getId()));
            approvalRecord.setApprovalStatus(subProject.getStatus());
            approvalRecord.setName(username);
            approvalRecord.setApprovalTime(new Date());
            approvalRecord.setType(ConstUtils.SUBPROJECTAPPROVALRECORDTYPE);
            approvalRecord.setApprovalDes(subProject.getApprovalDes());
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
