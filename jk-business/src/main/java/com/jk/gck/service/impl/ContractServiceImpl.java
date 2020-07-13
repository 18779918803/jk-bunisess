package com.jk.gck.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jk.common.base.BaseServiceImpl;
import com.jk.common.bean.PageHelper;
import com.jk.common.bean.Query;
import com.jk.gck.entity.ApprovalRecord;
import com.jk.gck.entity.Contract;
import com.jk.gck.entity.Count;
import com.jk.gck.mapper.ApprovalRecordMapper;
import com.jk.gck.mapper.ContractMapper;
import com.jk.gck.service.IContractService;
import com.jk.gck.utils.ConstUtils;
import com.jk.sys.entity.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 合同服务接口实现层
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年01月13日
 */
@Service
public class ContractServiceImpl extends BaseServiceImpl<ContractMapper, Contract> implements IContractService {

    @Autowired
    private ContractMapper contractMapper;

    @Autowired
    private ApprovalRecordMapper approvalRecordMapper;

    @Override
    public Map<String, Object> selectMapById(Integer contractid) {
        return contractMapper.selectMapById(contractid);
    }

    @Override
    public IPage selectAllContractByMainProject(Map para) {
        Query query = new Query(para);
        Page page = query.getPage();
        PageHelper.startPage(page);
        List list = baseMapper.selectAllContractByMainProject(para);
        page.setRecords(list);
        return page;
    }

    @Override
    public IPage selectAllContractBySubProject(Map para) {
        Query query = new Query(para);
        Page page = query.getPage();
        PageHelper.startPage(page);
        List list = baseMapper.selectAllContractBySubProject(para);
        page.setRecords(list);
        return page;
    }

    @Override
    public Map<String, Object> selectByContractId(Integer contractId) {
        return contractMapper.selectByContractId(contractId);
    }

    @Override
    public IPage selectAllContractByContract(Map para) {
        Query query = new Query(para);
        Page page = query.getPage();
        PageHelper.startPage(page);
        List list = baseMapper.selectAllContractByContract(para);
        page.setRecords(list);
        return page;
    }

    @Override
    public boolean addCommit(Contract contract) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String username = user.getUsername();
       // contract.existWarning();
        int row = contractMapper.updateById(contract);
        if (row > 0) {
            ApprovalRecord approvalRecord = new ApprovalRecord();
            approvalRecord.setApprovalId(Long.valueOf(contract.getId()));
            approvalRecord.setApprovalStatus(contract.getStatus());
            approvalRecord.setName(username);
            approvalRecord.setApprovalTime(new Date());
            approvalRecord.setType(ConstUtils.CONTRACTAPPROVALRECORDTYPE);
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
    public boolean addCheck(Contract contract) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String username = user.getUsername();
        int row = contractMapper.updateById(contract);
        if (row > 0) {
            ApprovalRecord approvalRecord = new ApprovalRecord();
            approvalRecord.setApprovalId(Long.valueOf(contract.getId()));
            approvalRecord.setApprovalStatus(contract.getStatus());
            approvalRecord.setName(username);
            approvalRecord.setApprovalTime(new Date());
            approvalRecord.setType(ConstUtils.CONTRACTAPPROVALRECORDTYPE);
            approvalRecord.setApprovalDes(contract.getApprovalDes());
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
    public Count getMainCount() {
        return contractMapper.getMainCount();
    }

    @Override
    public Long getWarningCount() {
        return contractMapper.getWarningCount();
    }

    @Override
    public Contract selectContractId(Integer id) {
        return contractMapper.selectContractId(id);
    }

    @Override
    public IPage selectContractByPartya(Map para) {
        Query query = new Query(para);
        Page page = query.getPage();
        PageHelper.startPage(page);
        List list = baseMapper.selectContractByPartya(para);
        page.setRecords(list);
        return page;
    }

    @Override
    public List selectContractAllByPartya(Map para) {
        List list = baseMapper.selectContractByPartya(para);
        return list;
    }

    @Override
    public Map selectAmountByContractId(Integer id) {
        return contractMapper.selectAmountByContractId(id);
    }


}
