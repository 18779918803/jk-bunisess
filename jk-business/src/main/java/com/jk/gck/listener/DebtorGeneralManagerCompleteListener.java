package com.jk.gck.listener;

import com.jk.gck.entity.Loan;
import com.jk.gck.mapper.LoanMapper;
import com.jk.gck.service.ILoanService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 监听贷款方总经理
 */
@Component
public class DebtorGeneralManagerCompleteListener implements TaskListener, ExecutionListener {

    private static final long serialVersionUID = 1L;


    private static ILoanService loanService;


    private static LoanMapper loanMapper;

    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void init() {
        loanService = context.getBean(ILoanService.class);
        loanMapper = context.getBean(LoanMapper.class);
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        Loan loan = loanService.selectById(new Integer(delegateTask.getExecution().getProcessInstanceBusinessKey()));
        delegateTask.setVariable("amount",loan.getAmount());
    }


    @Override
    public void notify(DelegateExecution delegateExecution) {
        Loan loan = loanService.selectById(new Integer(delegateExecution.getProcessInstanceBusinessKey()));
        delegateExecution.setVariable("amount",loan.getAmount());
    }
}
