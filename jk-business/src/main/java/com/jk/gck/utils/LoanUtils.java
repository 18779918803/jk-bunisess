package com.jk.gck.utils;

import com.jk.gck.entity.Loan;
import com.jk.sys.entity.Organ;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LoanUtils {

    /**
     * 通过还款时间算出还款利息
     *
     * @param loan
     * @param finishDate
     * @return
     */
    public static BigDecimal getRate(Loan loan, Date finishDate) {

        //获取两个日期间的天数
        Long day = getDay(loan.getTradeTime(), finishDate) + 1;

        //本金*年利率/365*天数
        BigDecimal result = loan.getAmount().multiply(loan.getRate()).divide(new BigDecimal("100")).divide(new BigDecimal("365"), BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(day + "")).setScale(2, BigDecimal.ROUND_HALF_UP);

        return result;
    }

    /**
     * 获取时间的后一天
     *
     * @param tradeDate
     * @return
     */
    public static Date getNextDay(Date tradeDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(tradeDate);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date result = (Date) calendar.getTime();
        return result;
    }

    /**
     * 获取两个时间的天数
     *
     * @param tradeTime
     * @param finishDate
     * @return
     */
    private static Long getDay(Date tradeTime, Date finishDate) {
        Long result = 0L;
        long tradeTimes = tradeTime.getTime();
        long finishDates = finishDate.getTime();
        result = (finishDates - tradeTimes) / (1000 * 60 * 60 * 24);
        return result;
    }

    public static String getNumber(Loan loan, Organ partyA, Organ partyB) {
        StringBuilder sb=new StringBuilder();
        sb.append("LOAN-");
        sb.append(partyA.getCode()+"-"+partyB.getCode());
        SimpleDateFormat  format=new SimpleDateFormat("yyyyMMdd");
        sb.append(format.format(loan.getTradeTime()));
        return sb.toString();
    }
}
