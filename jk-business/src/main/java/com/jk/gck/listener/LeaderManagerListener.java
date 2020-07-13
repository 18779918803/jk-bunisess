package com.jk.gck.listener;

import com.jk.gck.entity.Loan;
import com.jk.gck.mapper.LoanMapper;
import com.jk.gck.service.ILoanService;
import com.jk.gck.service.IUserOrganService;
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
public class LeaderManagerListener implements TaskListener {

    private static final long serialVersionUID = 1L;


    private static ILoanService loanService;


    private static LoanMapper loanMapper;

    @Autowired
    private ApplicationContext context;

    private static IUserOrganService userOrganService;

    @PostConstruct
    public void init() {
        loanService = context.getBean(ILoanService.class);
        loanMapper = context.getBean(LoanMapper.class);
        userOrganService=context.getBean(IUserOrganService.class);
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        Loan loan = loanService.selectById(new Integer(delegateTask.getExecution().getProcessInstanceBusinessKey()));
        Integer loanId = loan.getLeaderId();
        List<String>  list=  userOrganService.getUserOrganbyOrgan(loanId);
        delegateTask.addCandidateUsers(list);
    }


}
