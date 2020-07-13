package com.jk.gck.service;

import com.jk.common.base.IBaseService;
import com.jk.gck.entity.Payment;



/**
 * 付款服务接口层
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年01月14日
 */
public interface IPaymentService extends IBaseService<Payment> {

    boolean addCommit(Payment payment);
    boolean addCheck(Payment payment);


}
