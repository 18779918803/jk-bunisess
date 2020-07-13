package com.jk.gck.listener;

import com.jk.gck.entity.Loan;
import com.jk.gck.mapper.LoanMapper;
import com.jk.gck.service.ILoanService;
import com.jk.gck.utils.ConstUtils;
import com.jk.sys.entity.User;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 监听借款方会计审核
 */

@Component
public class LeaderViceGeneralManagerListener implements TaskListener {

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

        Integer loanId = loan.getLeaderId();
        List<User> users = loanMapper.getUserByRoleAndOrgan(ConstUtils.SUB_VICE_GENERAL_MANAGER, loanId);  //表示的是借款方会计
        List<String> list = new ArrayList<>();
        for (User user : users) {
            list.add(user.getUsername());
        }
        delegateTask.addCandidateUsers(list);
    }


}
