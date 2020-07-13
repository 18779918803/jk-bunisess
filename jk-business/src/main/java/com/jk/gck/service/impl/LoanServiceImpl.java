package com.jk.gck.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jk.activiti.service.IProcessService;
import com.jk.activiti.utils.DateUtils;
import com.jk.common.base.BaseServiceImpl;
import com.jk.common.bean.PageHelper;
import com.jk.common.bean.Query;
import com.jk.gck.entity.ApprovalRecord;
import com.jk.gck.entity.Loan;
import com.jk.gck.mapper.ApprovalRecordMapper;
import com.jk.gck.mapper.LoanMapper;
import com.jk.gck.service.ILoanService;
import com.jk.gck.utils.ConstUtils;
import com.jk.sys.entity.Role;
import com.jk.sys.entity.User;
import com.jk.sys.service.IOrganService;
import com.jk.sys.service.IUserService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 资金划拨服务接口实现层
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年05月13日
 */
@Service
public class LoanServiceImpl extends BaseServiceImpl<LoanMapper, Loan> implements ILoanService {


    @Autowired
    private LoanMapper loanMapper;

    @Autowired
    private ApprovalRecordMapper approvalRecordMapper;


    @Autowired
    private IProcessService processService;


    @Override
    public IPage selectDebtorAll(Map para) {
        Query query = new Query(para);
        Page page = query.getPage();
        PageHelper.startPage(page);
        List list = baseMapper.selectDebtorAll(para);
        page.setRecords(list);
        return page;
    }

    @Override
    public IPage selectLeaderAll(Map para) {
        Query query = new Query(para);
        Page page = query.getPage();
        PageHelper.startPage(page);
        List list = baseMapper.selectLeaderAll(para);
        page.setRecords(list);
        return page;
    }

    @Override
    public List selectDebtorAllList(Map para) {
        return loanMapper.selectDebtorAll(para);
    }

    @Override
    public List selectLeaderAllList(Map para) {
        return loanMapper.selectLeaderAll(para);
    }

    @Override
    public boolean addCommit(Loan loan) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String username = user.getUsername();
        int row = loanMapper.updateById(loan);
        if (row > 0) {
            ApprovalRecord approvalRecord = new ApprovalRecord();
            approvalRecord.setApprovalId(Long.valueOf(loan.getId()));
            approvalRecord.setApprovalStatus(loan.getStatus());
            approvalRecord.setName(username);
            approvalRecord.setApprovalTime(new Date());
            approvalRecord.setType(ConstUtils.LOANAPPROVALRECORDTYPE);
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
    public boolean addCheck(Loan loan) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String username = user.getUsername();
        int row = loanMapper.updateById(loan);
        if (row > 0) {
            ApprovalRecord approvalRecord = new ApprovalRecord();
            approvalRecord.setApprovalId(Long.valueOf(loan.getId()));
            approvalRecord.setApprovalStatus(loan.getStatus());
            approvalRecord.setName(username);
            approvalRecord.setApprovalTime(new Date());
            approvalRecord.setType(ConstUtils.LOANAPPROVALRECORDTYPE);
            approvalRecord.setApprovalDes(loan.getApprovalDes());
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
    public IPage selectLeaderAndDebtorAll(Map para) {
        Query query = new Query(para);
        Page page = query.getPage();
        PageHelper.startPage(page);
        List list = baseMapper.selectLeaderAndDebtorAll(para);
        page.setRecords(list);
        return page;
    }

    @Override
    public IPage selectDebtorAndLeaderAll(Map para) {
        Query query = new Query(para);
        Page page = query.getPage();
        PageHelper.startPage(page);
        List list = baseMapper.selectDebtorAndLeaderAll(para);
        page.setRecords(list);
        return page;
    }

    @Override
    public ProcessInstance submitApply(Loan loan, String applyUserId, String key, Map<String, Object> variables) {
        loan.setApplyUser(applyUserId);
        loan.setApplyTime(DateUtils.getNowDate());
        loanMapper.updateById(loan);
        String businessKey = loan.getId().toString(); // 实体类 ID，作为流程的业务 key
        ProcessInstance processInstance = processService.submitApply(applyUserId, businessKey, loan.getNumber(), loan.getRemark(), key, variables);
        String processInstanceId = processInstance.getId();
        loan.setInstanceId(processInstanceId); // 建立双向关系
        loanMapper.updateById(loan);
        return processInstance;
    }

    @Override
    public List selectLeaderAndDebtorAllList(Map para) {
        return loanMapper.selectLeaderAndDebtorAll(para);
    }

    @Override
    public List selectDebtorAndLeaderAllList(Map para) {
        return loanMapper.selectDebtorAndLeaderAll(para);
    }

    @Override
    public Map selectByLoanId(Integer id) {
        return loanMapper.selectByLoanId(id);
    }


    //通过名字去查角色
    @Override
    public List selectLoanList(String name) {
        Integer userId = loanMapper.selectUserIdByName(name);
        List<Role> roles = loanMapper.selectUserRoles(userId);
        List loans = new ArrayList<Loan>();
        for (int i = 0; i < roles.size(); i++) {
            if (roles.get(i).getCode().equals("001")) {  //表示的是董事长审批
                loans = loanMapper.selectByLoanState("5");
            } else if (roles.get(i).getCode().equals("002")) {//表示的是总经理审批
                loans = loanMapper.selectByLoanState("4");
            } else if (roles.get(i).getCode().equals("003")) {  //表示的是财务部分管领导审批
                loans = loanMapper.selectByLoanState("3");
            } else if (roles.get(i).getCode().equals("004")) {  //表示的是财务负责人审批
                loans = loanMapper.selectByLoanState("2");
            }
        }
        return loans;
    }

    @Override
    public Map selectLoanMapById(Integer id) {
        return loanMapper.selectLoanMapById(id);
    }


    @Override
    public List selectLoansList(Integer page) {
        int size = 3;
        int start = (page - 1) * size;
        return loanMapper.selectByLoan(start);
    }

    @Override
    public List<User> getUserByRoleAndOrgan(String code, Integer organId) {
        return loanMapper.getUserByRoleAndOrgan(code,organId);
    }
}
