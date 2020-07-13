package com.jk.gck.listener;

import com.jk.gck.entity.Loan;
import com.jk.gck.mapper.LoanMapper;
import com.jk.gck.service.ILoanService;
import com.jk.gck.service.IUserOrganService;
import com.jk.sys.entity.Organ;
import com.jk.sys.service.IOrganService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 监听贷款方分管领导审核
 */

@Component
public class FinanceManagerListener implements TaskListener {

    private static final long serialVersionUID = 1L;


    private static ILoanService loanService;


    private static LoanMapper loanMapper;

    @Autowired
    private ApplicationContext context;

    private static IUserOrganService userOrganService;

    private static IOrganService organService;

    @PostConstruct
    public void init() {
        loanService = context.getBean(ILoanService.class);
        loanMapper = context.getBean(LoanMapper.class);
        userOrganService = context.getBean(IUserOrganService.class);
        organService = context.getBean(IOrganService.class);
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        Loan loan = loanService.selectById(new Integer(delegateTask.getExecution().getProcessInstanceBusinessKey()));
        //表示的是财务部财务部分管领导。
        String code = "0101";  //集团财务部的组织code
        Organ organ = organService.selectByCode(code);
        Integer fianceId = organ.getId();
        List<String> list = userOrganService.getUserOrganbyOrgan(fianceId);
        delegateTask.addCandidateUsers(list);
    }

}
