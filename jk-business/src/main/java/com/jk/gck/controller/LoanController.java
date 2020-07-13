package com.jk.gck.controller;


import cn.afterturn.easypoi.entity.vo.MapExcelConstants;
import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.activiti.service.IProcessService;
import com.jk.common.annotation.KrtLog;
import com.jk.common.base.BaseController;
import com.jk.common.bean.DataTable;
import com.jk.common.bean.ReturnBean;
import com.jk.common.util.ServletUtils;
import com.jk.common.util.ShiroUtils;
import com.jk.common.validator.Assert;
import com.jk.gck.entity.Loan;
import com.jk.gck.entity.LoanReturn;
import com.jk.gck.service.IEntityService;
import com.jk.gck.service.ILoanReturnService;
import com.jk.gck.service.ILoanService;
import com.jk.gck.utils.ConstUtils;
import com.jk.gck.utils.LoanUtils;
import com.jk.sys.entity.Organ;
import com.jk.sys.service.IOrganService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.TaskEntityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 资金划拨控制层
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年05月13日
 */
@Controller
public class LoanController extends BaseController {

    @Autowired
    private ILoanService loanService;

    @Autowired
    private IEntityService iEntityService;

    @Autowired
    private ILoanReturnService iLoanReturnService;


    @Autowired
    private TaskService taskService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private IOrganService organService;

    @Autowired
    private IProcessService processService;



    /**
     * 资金划拨管理页
     *
     * @return {@link String}
     */
    @RequiresPermissions("Loan:loan:list")
    @GetMapping("gck/loan/list")
    public String list(@RequestParam(value = "debtorId", required = false, defaultValue = "-1") String debtorId, @RequestParam(value = "leaderId", required = false, defaultValue = "-1") String leaderId) {
        //甲方
        Map<String, Object> param = new HashedMap<>();
        param.put("is_internal", ConstUtils.ISTHREE);
        if (!("-1").equals(debtorId)) {
            request.setAttribute("debtorId", debtorId);
        }
        if (!("-1").equals(leaderId)) {
            request.setAttribute("leaderId", leaderId);
        }
        request.setAttribute("currentUser", ShiroUtils.getSessionUser());
        List<Organ> organs = organService.selectList();
        request.setAttribute("partyAs", organs);
        return "gck/loan/list";
    }

    /**
     * 资金划拨管理
     *
     * @param para 搜索参数
     * @return {@link DataTable}
     */
    @RequiresPermissions("Loan:loan:list")
    @PostMapping("gck/loan/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para) throws ParseException {
        if (!ShiroUtils.getSessionUser().isAdmin()) {
            para.put("createBy", ShiroUtils.getSessionUser().getUsername());
        }
        IPage page = loanService.selectPageMap(para);
        List<Map> results = page.getRecords();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for (Map map : results) {
            Integer id = (Integer) map.get("id");
            Loan loan = loanService.selectById(id);
            List<LoanReturn> result = iLoanReturnService.getAmountById(id);
            if (result == null || result.size() == 0) {  //表示的是第一次还本金和利息
                map.put("shouldAmount", loan.getAmount());
                BigDecimal rate = BigDecimal.ZERO;
                if (para.get("startTime") != null && !("").equals(para.get("startTime"))) {
                    Date startTime = format.parse((String) para.get("startTime"));
                    loan.setTradeTime(startTime);
                }
                if (para.get("returnTime") != null && !("").equals(para.get("returnTime"))) {
                    Date returnTime = format.parse((String) para.get("returnTime"));
                    rate = LoanUtils.getRate(loan, returnTime);
                } else {
                    rate = LoanUtils.getRate(loan, loan.getReturnTime());
                }
                map.put("shouldRate", rate);
            } else {
                BigDecimal amountSum = BigDecimal.ZERO;  //表示的是本金
                BigDecimal rateSum = BigDecimal.ZERO;  //表示的是利息
                for (LoanReturn loanReturn : result) {
                    BigDecimal amount = BigDecimal.ZERO;
                    if (loanReturn.getAmount() != null) {
                        amount = loanReturn.getAmount();
                    }
                    amountSum = amountSum.add(amount);
                    BigDecimal rate = BigDecimal.ZERO;
                    if (loanReturn.getRate() != null) {
                        rate = loanReturn.getRate();
                    }
                    BigDecimal shouldRate = BigDecimal.ZERO;
                    if (loanReturn.getShouldRate() != null) {
                        shouldRate = loanReturn.getShouldRate();
                    }
                    rateSum = rateSum.add(shouldRate.subtract(rate));
                }

                Date tradeTime = result.get(0).getTradeTime();  //表示最后一次付款时间。
                loan.setTradeTime(LoanUtils.getNextDay(tradeTime));
                loan.setAmount(loan.getAmount().subtract(amountSum));
                if (para.get("startTime") != null && !("").equals(para.get("startTime"))) {
                    Date startTime = format.parse((String) para.get("startTime"));
                    loan.setTradeTime(startTime);
                }
                if (para.get("returnTime") != null && !("").equals(para.get("returnTime"))) {
                    Date returnTime = format.parse((String) para.get("returnTime"));
                    rateSum = rateSum.add(LoanUtils.getRate(loan, returnTime));
                } else {
                    rateSum = rateSum.add(LoanUtils.getRate(loan, loan.getReturnTime()));
                }

                map.put("shouldAmount", loan.getAmount());
                map.put("shouldRate", rateSum);


            }

            // 当前环节
            if (map.get("instanceId") != null && StringUtils.isNotBlank((String) map.get("instanceId"))) {
                List<Task> taskList = taskService.createTaskQuery()
                        .processInstanceId((String) map.get("instanceId"))
                        .list();    // 例如请假会签，会同时拥有多个任务
                if (!CollectionUtils.isEmpty(taskList)) {
                    TaskEntityImpl task = (TaskEntityImpl) taskList.get(0);
                    map.put("taskId", task.getId());
                    if (task.getSuspensionState() == 2) {
                        map.put("taskName", "已挂起");
                        map.put("suspendState", "2");
                    } else {

                        map.put("taskName", task.getName());
                        map.put("suspendState", "1");
                    }
                } else {
                    // 已办结或者已撤销
                    map.put("taskName", "已结束");
                }
            } else {
                map.put("taskName", "未启动");
            }


        }

        return DataTable.ok(page);
    }

    /**
     * 新增资金划拨页
     *
     * @return {@link String}
     */
    @RequiresPermissions("Loan:loan:insert")
    @GetMapping("gck/loan/insert")
    public String insert() {
        Map<String, Object> param = new HashedMap<>();
//        param.put("is_internal", ConstUtils.ISTHREE);
//
//        Collection partyAs = iEntityService.selectByMap(param);

        List<Organ> organs = organService.selectList();
        request.setAttribute("partyAs", organs);
        return "gck/loan/insert";
    }


    /**
     * 提交申请
     */
    @KrtLog("提交申请")
    @PostMapping("gck/loan/submitApply")
    @ResponseBody
    public ReturnBean submitApply(Long id) {
        if (ShiroUtils.getSessionUser().isAdmin()) {
            return ReturnBean.error("提交申请失败：不允许管理员提交申请！");
        }
        Loan loan = loanService.selectById(id);
        String applyUserId = ShiroUtils.getSessionUser().getName();
        Map param= new HashMap<>();
        param.put("amount",loan.getAmount());
        if(loan.getLeaderId()==40){  //表示的是子公司向集团总部
            param.put("main",true);
        }else{     //表示的是子公司之间借款
            param.put("main",false);
        }
        if(loan.getDebtorId()==40){     //表示的是集团内部向子公司自己调拨
            param.put("type",true);
        }else{                      //表示的是子公司向集团内部借款或者是子公司之间借款
            param.put("type",false);
        }

        loanService.submitApply(loan, applyUserId, "loan", param);
        return ReturnBean.ok();
    }

    /**
     * 添加资金划拨
     *
     * @param loan 资金划拨
     * @return {@link ReturnBean}
     */
    @KrtLog("添加资金划拨")
    @RequiresPermissions("Loan:loan:insert")
    @PostMapping("gck/loan/insert")
    @ResponseBody
    public ReturnBean insert(Loan loan) {
        loan.setStatus(ConstUtils.APPROVALUNCOMMIT);  //表示的是审批的状态
        loan.setState(ConstUtils.UNFINSh);  //表示的是贷款是否还清
//        Entity partyA = iEntityService.selectById(loan.getDebtorId());
//        Entity partyB = iEntityService.selectById(loan.getLeaderId());

        Organ partyA = organService.selectById(loan.getDebtorId());
        Organ partyB = organService.selectById(loan.getLeaderId());

        String number = LoanUtils.getNumber(loan, partyA, partyB);
        loan.setNumber(number);
        if (ShiroUtils.getSessionUser().isAdmin()) {
            return ReturnBean.error("提交申请失败：不允许管理员提交申请！");
        }
        loan.setCreateBy(ShiroUtils.getSessionUser().getUsername());
        loanService.insert(loan);
        return ReturnBean.ok();
    }

    /**
     * 修改资金划拨页
     *
     * @param id 资金划拨id
     * @return {@link String}
     */
    @RequiresPermissions("Loan:loan:returnAmount")
    @GetMapping("gck/loan/returnAmount")
    public String returnAmount(Integer id) {
        Loan loan = loanService.selectById(id);
        request.setAttribute("loan", loan);
        //表示的是已经收的本金和利息
        List<LoanReturn> result = iLoanReturnService.getAmountById(id);
        if (result == null || result.size() == 0) {  //表示的是第一次还本金和利息
            request.setAttribute("shouldAmount", loan.getAmount());
            BigDecimal rate = LoanUtils.getRate(loan, loan.getReturnTime());
            request.setAttribute("shouldRate", rate);
        } else {
            BigDecimal amountSum = BigDecimal.ZERO;  //表示的是本金
            BigDecimal rateSum = BigDecimal.ZERO;  //表示的是利息
            for (LoanReturn loanReturn : result) {
                BigDecimal amount = BigDecimal.ZERO;
                if (loanReturn.getAmount() != null) {
                    amount = loanReturn.getAmount();
                }
                amountSum = amountSum.add(amount);
                BigDecimal rate = BigDecimal.ZERO;
                if (loanReturn.getRate() != null) {
                    rate = loanReturn.getRate();
                }
                BigDecimal shouldRate = BigDecimal.ZERO;
                if (loanReturn.getShouldRate() != null) {
                    shouldRate = loanReturn.getShouldRate();
                }
                rateSum = rateSum.add(shouldRate.subtract(rate));
            }

            Date tradeTime = result.get(0).getTradeTime();  //表示最后一次付款时间。
            loan.setTradeTime(LoanUtils.getNextDay(tradeTime));
            loan.setAmount(loan.getAmount().subtract(amountSum));
            rateSum = rateSum.add(LoanUtils.getRate(loan, loan.getReturnTime()));
         /*   resultMap.put("principal", loan.getAmount().subtract(amountSum));  //表示的是应还本金
            resultMap.put("rate", rateSum);*/
            request.setAttribute("shouldAmount", loan.getAmount());
            request.setAttribute("shouldRate", rateSum);
        }
        return "gck/loan/return";
    }


    /**
     * 借款方
     *
     * @return
     */
    @RequiresPermissions("Loan:loan:totalDebtor")
    @GetMapping("gck/loan/totalDebtor")
    public String totalDebtor() {
        Map<String, Object> param = new HashedMap<>();
        param.put("is_internal", ConstUtils.ISTHREE);

        Collection partyAs = iEntityService.selectByMap(param);
        request.setAttribute("partyAs", partyAs);
        return "gck/loan/totalDebtor";
    }

    /**
     * 借款方
     *
     * @return
     */
    @RequiresPermissions("Loan:loan:totalDebtor")
    @PostMapping("gck/loan/totalDebtor")
    @ResponseBody
    public DataTable totalDebtor(@RequestParam Map para) {
        //IPage page = contractService.selectPageList(para);
        IPage page = loanService.selectDebtorAll(para);
        return DataTable.ok(page);
    }

    /**
     * 借款方和贷款方汇总
     *
     * @return
     */
    @RequiresPermissions("Loan:loan:totalDebtorAndLeader")
    @GetMapping("gck/loan/totalDebtorAndLeader")
    public String totalDebtorAndLeader() {
        Map<String, Object> param = new HashedMap<>();
        param.put("is_internal", ConstUtils.ISTHREE);

        Collection partyAs = iEntityService.selectByMap(param);
        request.setAttribute("partyAs", partyAs);
        return "gck/loan/totalDebtorAndLeader";
    }

    /**
     * 借款方和贷款方汇总
     *
     * @return
     */
    @RequiresPermissions("Loan:loan:totalDebtorAndLeader")
    @PostMapping("gck/loan/totalDebtorAndLeader")
    @ResponseBody
    public DataTable totalDebtorAndLeader(@RequestParam Map para) {
        //IPage page = contractService.selectPageList(para);
        IPage page = loanService.selectDebtorAndLeaderAll(para);
        return DataTable.ok(page);
    }


    /**
     * 贷款方和借款方汇总
     *
     * @return
     */
    @RequiresPermissions("Loan:loan:totalLeaderAndDebtor")
    @GetMapping("gck/loan/totalLeaderAndDebtor")
    public String totalLeaderAndDebtor() {
        Map<String, Object> param = new HashedMap<>();
        param.put("is_internal", ConstUtils.ISTHREE);

        Collection partyAs = iEntityService.selectByMap(param);
        request.setAttribute("partyAs", partyAs);
        return "gck/loan/totalLeaderAndDebtor";
    }


    /**
     * 贷款方和借款方汇总
     *
     * @return
     */
    @RequiresPermissions("Loan:loan:totalLeaderAndDebtor")
    @PostMapping("gck/loan/totalLeaderAndDebtor")
    @ResponseBody
    public DataTable totalLeaderAndDebtor(@RequestParam Map para) {
        IPage page = loanService.selectLeaderAndDebtorAll(para);
        return DataTable.ok(page);
    }

    /**
     * 贷款方
     *
     * @return
     */
    @RequiresPermissions("Loan:loan:totalLeader")
    @GetMapping("gck/loan/totalLeader")
    public String totalLeader() {
        Map<String, Object> param = new HashedMap<>();
        param.put("is_internal", ConstUtils.ISTHREE);

        Collection partyAs = iEntityService.selectByMap(param);
        request.setAttribute("partyAs", partyAs);
        return "gck/loan/totalLeader";
    }


    /**
     * 贷款方
     *
     * @return
     */
    @RequiresPermissions("Loan:loan:totalLeader")
    @PostMapping("gck/loan/totalLeader")
    @ResponseBody
    public DataTable totalLeader(@RequestParam Map para) {
        //IPage page = contractService.selectPageList(para);
        IPage page = loanService.selectLeaderAll(para);
        return DataTable.ok(page);
    }

    /**
     * 修改资金划拨页
     *
     * @param returnTime 资金划拨id
     * @return {@link String}
     */
    @RequiresPermissions("Loan:loan:list")
    @GetMapping("gck/loan/getShouldAmount")
    @ResponseBody
    public ReturnBean getShouldAmount(@Param("returnTime") String returnTime, Integer id) throws ParseException {
        Loan loan = loanService.selectById(id);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date returnDate = format.parse(returnTime);
        //1、第一步算出截止到完成时间本金和利息
        if (returnDate.compareTo(loan.getTradeTime()) < 0) {  //表示的是
            return ReturnBean.error("选择日期小于贷款时间");
        }
        Map resultMap = new HashMap();
        //表示的是已经收的本金和利息
        List<LoanReturn> result = iLoanReturnService.getAmountById(id);
        if (result == null || result.size() == 0) {  //表示的是第一次还本金和利息
            resultMap.put("principal", loan.getAmount());
            BigDecimal rate = LoanUtils.getRate(loan, returnDate);
            resultMap.put("rate", rate);
            return ReturnBean.ok(resultMap);
        } else {
            BigDecimal amountSum = BigDecimal.ZERO;  //表示的是本金
            BigDecimal rateSum = BigDecimal.ZERO;  //表示的是利息
            for (LoanReturn loanReturn : result) {
                BigDecimal amount = BigDecimal.ZERO;
                if (loanReturn.getAmount() != null) {
                    amount = loanReturn.getAmount();
                }
                amountSum = amountSum.add(amount);
                BigDecimal rate = BigDecimal.ZERO;
                if (loanReturn.getRate() != null) {
                    rate = loanReturn.getRate();
                }
                BigDecimal shouldRate = BigDecimal.ZERO;
                if (loanReturn.getShouldRate() != null) {
                    shouldRate = loanReturn.getShouldRate();
                }
                rateSum = rateSum.add(shouldRate.subtract(rate));
            }
            Date tradeTime = result.get(0).getTradeTime();  //表示最后一次付款时间。
            loan.setTradeTime(LoanUtils.getNextDay(tradeTime));
            loan.setAmount(loan.getAmount().subtract(amountSum));
            rateSum = rateSum.add(LoanUtils.getRate(loan, returnDate));
            resultMap.put("principal", loan.getAmount());  //表示的是应还本金
            resultMap.put("rate", rateSum);
            return ReturnBean.ok(resultMap);
        }
    }


    /**
     * 修改资金划拨页
     *
     * @param id 资金划拨id
     * @return {@link String}
     */
    @RequiresPermissions("Loan:loan:update")
    @GetMapping("gck/loan/update")
    public String update(Integer id) {
        Map<String, Object> param = new HashedMap<>();
        param.put("is_internal", ConstUtils.ISTHREE);
        Collection partyAs = iEntityService.selectByMap(param);
        request.setAttribute("partyAs", partyAs);
        Loan loan = loanService.selectById(id);
        request.setAttribute("loan", loan);
        return "gck/loan/update";
    }

    /**
     * 修改资金划拨
     *
     * @param loan 资金划拨
     * @return {@link ReturnBean}
     */
    @KrtLog("修改资金划拨")
    @RequiresPermissions("Loan:loan:update")
    @PostMapping("gck/loan/update")
    @ResponseBody
    public ReturnBean update(Loan loan) {
        loanService.updateById(loan);
        return ReturnBean.ok();
    }

    /**
     * 删除资金划拨
     *
     * @param id 资金划拨id
     * @return {@link ReturnBean}
     */
    @KrtLog("删除资金划拨")
    @RequiresPermissions("Loan:loan:delete")
    @PostMapping("gck/loan/delete")
    @ResponseBody
    public ReturnBean delete(Integer id) {
        loanService.deleteById(id);
        return ReturnBean.ok();
    }

    /**
     * 批量删除资金划拨
     *
     * @param ids 资金划拨ids
     * @return {@link ReturnBean}
     */
    @KrtLog("批量删除资金划拨")
    @RequiresPermissions("Loan:loan:delete")
    @PostMapping("gck/loan/deleteByIds")
    @ResponseBody
    public ReturnBean deleteByIds(Integer[] ids) {
        loanService.deleteByIds(Arrays.asList(ids));
        return ReturnBean.ok();
    }

    /**
     * 导入资金划拨
     *
     * @param file excel文件
     * @throws Exception 异常
     */
    @KrtLog("导入资金划拨")
    @RequiresPermissions("Loan:loan:excelIn")
    @PostMapping("gck/loan/excelIn")
    public void excelIn(@RequestParam("file") MultipartFile file) throws Exception {
        Assert.isExcel(file);
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        params.setNeedSave(false);
        //读取excel
        List<Loan> dataList = ExcelImportUtil.importExcel(file.getInputStream(), Loan.class, params);
        loanService.insertBatch(dataList);
        ServletUtils.printText(response, JSON.toJSONString(ReturnBean.ok()));
    }

    /**
     * 导出资金划拨
     *
     * @param modelMap 返回模型
     * @param para     参数
     */
    @KrtLog("导出资金划拨")
    @RequiresPermissions("Loan:loan:excelOut")
    @GetMapping("gck/loan/excelOut")
    public String excelOut(ModelMap modelMap, Map para) {
        List<ExcelExportEntity> entityList = new ArrayList<>();
        entityList.add(new ExcelExportEntity("借款协议编号", "number", 15));
        entityList.add(new ExcelExportEntity("资金调入公司", "debtorName", 15));
        entityList.add(new ExcelExportEntity("资金调出公司", "leaderName", 15));
        entityList.add(new ExcelExportEntity("代款金额", "amount", 15));
        entityList.add(new ExcelExportEntity("计息时间", "tradeTime", 15));
        entityList.add(new ExcelExportEntity("利息", "rate", 15));
        entityList.add(new ExcelExportEntity("应归还时间", "returnTime", 15));
        entityList.add(new ExcelExportEntity("是否还完", "stateName", 15));
        entityList.add(new ExcelExportEntity("备注", "remark", 15));
        List dataResult = loanService.selectExcelList(para);
        modelMap.put(MapExcelConstants.ENTITY_LIST, entityList);
        modelMap.put(MapExcelConstants.MAP_LIST, dataResult);
        modelMap.put(MapExcelConstants.FILE_NAME, "资金划拨");
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("资金划拨", "资金划拨"));
        return MapExcelConstants.EASYPOI_MAP_EXCEL_VIEW;
    }


    /**
     * 导出借款
     *
     * @param modelMap 返回模型
     * @param para     参数
     */
    @KrtLog("导出借款")
    @RequiresPermissions("Loan:loan:excelOut")
    @GetMapping("gck/loan/debtorExcelOut")
    public String debtorExcelOut(ModelMap modelMap, Map para) {
        List<ExcelExportEntity> entityList = new ArrayList<>();
        entityList.add(new ExcelExportEntity("借款公司", "debtorName", 15));
        entityList.add(new ExcelExportEntity("借款金额", "amount", 15));
        entityList.add(new ExcelExportEntity("已还本金", "realityAmount", 15));
        entityList.add(new ExcelExportEntity("已还利息", "realityRate", 15));
        List dataResult = loanService.selectDebtorAllList(para);
        modelMap.put(MapExcelConstants.ENTITY_LIST, entityList);
        modelMap.put(MapExcelConstants.MAP_LIST, dataResult);
        modelMap.put(MapExcelConstants.FILE_NAME, "借款汇总");
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("借款汇总", "借款汇总"));
        return MapExcelConstants.EASYPOI_MAP_EXCEL_VIEW;
    }

    /**
     * 导出借款
     *
     * @param modelMap 返回模型
     * @param para     参数
     */
    @KrtLog("导出借款")
    @RequiresPermissions("Loan:loan:excelOut")
    @GetMapping("gck/loan/debtorAndLeaderExcelOut")
    public String debtorAndLeaderExcelOut(ModelMap modelMap, Map para) {
        List<ExcelExportEntity> entityList = new ArrayList<>();
        entityList.add(new ExcelExportEntity("借款公司", "debtorName", 15));
        entityList.add(new ExcelExportEntity("贷款公司", "leaderName", 15));
        entityList.add(new ExcelExportEntity("借款金额", "amount", 15));
        entityList.add(new ExcelExportEntity("借款已还本金", "realityAmount", 15));
        entityList.add(new ExcelExportEntity("借款已还利息", "realityRate", 15));
        List dataResult = loanService.selectDebtorAndLeaderAllList(para);
        modelMap.put(MapExcelConstants.ENTITY_LIST, entityList);
        modelMap.put(MapExcelConstants.MAP_LIST, dataResult);
        modelMap.put(MapExcelConstants.FILE_NAME, "借款汇总明细");
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("借款汇总明细", "借款汇总明细"));
        return MapExcelConstants.EASYPOI_MAP_EXCEL_VIEW;
    }


    /**
     * 导出贷款
     *
     * @param modelMap 返回模型
     * @param para     参数
     */
    @KrtLog("导出贷款")
    @RequiresPermissions("Loan:loan:excelOut")
    @GetMapping("gck/loan/leaderExcelOut")
    public String leaderExcelOut(ModelMap modelMap, Map para) {
        List<ExcelExportEntity> entityList = new ArrayList<>();
        entityList.add(new ExcelExportEntity("贷款公司", "leaderName", 15));
        entityList.add(new ExcelExportEntity("贷款金额", "amount", 15));
        entityList.add(new ExcelExportEntity("贷款实收本金", "realityAmount", 15));
        entityList.add(new ExcelExportEntity("贷款实收利息", "realityRate", 15));
        List dataResult = loanService.selectLeaderAllList(para);
        modelMap.put(MapExcelConstants.ENTITY_LIST, entityList);
        modelMap.put(MapExcelConstants.MAP_LIST, dataResult);
        modelMap.put(MapExcelConstants.FILE_NAME, "贷款汇总");
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("贷款汇总", "贷款汇总"));
        return MapExcelConstants.EASYPOI_MAP_EXCEL_VIEW;
    }

    /**
     * 导出贷款
     *
     * @param modelMap 返回模型
     * @param para     参数
     */
    @KrtLog("导出贷款")
    @RequiresPermissions("Loan:loan:excelOut")
    @GetMapping("gck/loan/leaderAndDebtorExcelOut")
    public String leaderAndDebtorExcelOut(ModelMap modelMap, Map para) {
        List<ExcelExportEntity> entityList = new ArrayList<>();
        entityList.add(new ExcelExportEntity("贷款公司", "leaderName", 15));
        entityList.add(new ExcelExportEntity("借款公司", "debtorName", 15));
        entityList.add(new ExcelExportEntity("贷款金额", "amount", 15));
        entityList.add(new ExcelExportEntity("贷款实收本金", "realityAmount", 15));
        entityList.add(new ExcelExportEntity("贷款实收利息", "realityRate", 15));
        List dataResult = loanService.selectLeaderAndDebtorAllList(para);
        modelMap.put(MapExcelConstants.ENTITY_LIST, entityList);
        modelMap.put(MapExcelConstants.MAP_LIST, dataResult);
        modelMap.put(MapExcelConstants.FILE_NAME, "贷款汇总");
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("贷款汇总", "贷款汇总"));
        return MapExcelConstants.EASYPOI_MAP_EXCEL_VIEW;
    }


    @KrtLog("提交审核")
    @RequiresPermissions("Loan:loan:commit")
    @PostMapping("gck/loan/commit")
    @ResponseBody
    public ReturnBean commit(Integer id) {
        Loan loan = loanService.selectById(id);
        loan.setStatus(ConstUtils.APPROVALCOMMIT);  //表示的是审核通过
        boolean flag = loanService.addCommit(loan);
        if (flag) {
            return ReturnBean.ok();
        } else {
            return ReturnBean.error();
        }
    }


    /**
     * 审核主项目页
     *
     * @param id 主项目id
     * @return {@link String}
     */
    @RequiresPermissions("Loan:loan:check")
    @GetMapping("gck/loan/check")
    public String check(Integer id) {
        Loan loan = loanService.selectById(id);
        request.setAttribute("loan", loan);
        return "gck/loan/check";
    }


    @KrtLog("审核")
    @RequiresPermissions("Loan:loan:check")
    @PostMapping("gck/loan/check")
    @ResponseBody
    public ReturnBean check(Loan loan) {
        loanService.addCheck(loan);
        return ReturnBean.ok();
    }


    @KrtLog("打印")
    @RequiresPermissions("Loan:loan:list")
    @GetMapping("gck/loan/print")
    public String print(Integer loanId) {
        Loan loan = loanService.selectById(loanId);
        request.setAttribute("loan", loan);
        Map<String, Object> param = new HashedMap<>();
        param.put("is_internal", ConstUtils.ISTHREE);
        Collection partyAs = iEntityService.selectByMap(param);
        request.setAttribute("partyAs", partyAs);
        return "gck/loan/print";
    }


    /**
     * 导出合同信息表
     *
     * @param modelMap 返回模型
     * @param para     参数
     */
    @KrtLog("导出借款协议")
    @RequiresPermissions("Loan:loan:excelOut")
    @GetMapping("gck/loan/loanExcelOut")
    public String loanExcelOut(ModelMap modelMap, Map para, String id) throws IOException {
        JSONObject retval = new JSONObject();
        TemplateExportParams params = new TemplateExportParams("excel/loan.xlsx");
        params.setSheetName("借款协议");
        Map map = new HashMap<>();
        Map result = loanService.selectByLoanId(Integer.parseInt(id));
        map.put("loan", result);
        // excel导出
        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
        // 下载
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("借款协议.xlsx", "UTF-8"));
        workbook.write(response.getOutputStream());

        return retval.toString();
    }

    /**
     * 导出合同信息表
     *
     * @param modelMap 返回模型
     * @param para     参数
     */
    @KrtLog("导出借款审批表")
    @RequiresPermissions("Loan:loan:excelOut")
    @GetMapping("gck/loan/checkExcelOut")
    public String checkExcelOut(ModelMap modelMap, Map para, String id) throws IOException {
        JSONObject retval = new JSONObject();
        TemplateExportParams params = new TemplateExportParams("excel/checkLoan.xlsx");
        params.setSheetName("借款审批表");
        Map map = new HashMap<>();
        Map result = loanService.selectByLoanId(Integer.parseInt(id));
        map.put("loan", result);
        // excel导出
        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
        // 下载
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("借款审批表.xlsx", "UTF-8"));
        workbook.write(response.getOutputStream());
        return retval.toString();
    }


    /**
     * 导出合同信息表
     *
     * @param modelMap 返回模型
     * @param para     参数
     */
    @KrtLog("导出建控集团审批表")
    @RequiresPermissions("Loan:loan:excelOut")
    @GetMapping("gck/loan/approvalExcelOut")
    public String approvalExcelOut(ModelMap modelMap, Map para, String id) throws IOException {
        JSONObject retval = new JSONObject();
        TemplateExportParams params = new TemplateExportParams("excel/approvalLoan.xlsx");
        params.setSheetName("资金调拨审批单");
        Map map = new HashMap<>();
        Map result = loanService.selectByLoanId(Integer.parseInt(id));
        map.put("loan", result);
        // excel导出
        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
        // 下载
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("资金调拨审批单.xlsx", "UTF-8"));
        workbook.write(response.getOutputStream());
        return retval.toString();
    }

    /**
     * 导出合同信息表
     *
     * @param modelMap 返回模型
     * @param para     参数
     */
    @KrtLog("借款申请表")
    @RequiresPermissions("Loan:loan:excelOut")
    @GetMapping("gck/loan/joinExcelOut")
    public String joinExcelOut(ModelMap modelMap, Map para, String id) throws IOException {
        JSONObject retval = new JSONObject();
        TemplateExportParams params = new TemplateExportParams("excel/joinLoan.xlsx");
        params.setSheetName("借款申请表");
        Map map = new HashMap<>();
        Map result = loanService.selectByLoanId(Integer.parseInt(id));
        map.put("loan", result);
        // excel导出
        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
        // 下载
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("借款申请表.xlsx", "UTF-8"));
        workbook.write(response.getOutputStream());
        return retval.toString();
    }


    /**
     * 加载审批弹窗
     *
     * @param taskId
     * @param mmap
     * @return
     */
    @GetMapping("loan/showVerifyDialog/{taskId}")
    public String showVerifyDialog(@PathVariable("taskId") String taskId, ModelMap mmap) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        Loan loan = loanService.selectById(new Integer(processInstance.getBusinessKey()));
        request.setAttribute("loan", loan);
        request.setAttribute("taskId", taskId);
        List<Organ> organs = organService.selectList();
        request.setAttribute("partyAs", organs);
        String verifyName = task.getTaskDefinitionKey().substring(0, 1).toUpperCase() + task.getTaskDefinitionKey().substring(1);
        return "gck/loan/task" + verifyName;
    }


    /**
     * 完成任务
     *
     * @return
     */
    @RequestMapping(value = "gck/loan/complete/{taskId}", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ReturnBean complete(@PathVariable("taskId") String taskId, @RequestParam(value = "saveEntity", required = false) String saveEntity,
                               @ModelAttribute("loan") Loan loan, HttpServletRequest request) {
        boolean saveEntityBoolean = BooleanUtils.toBoolean(saveEntity);
        Map param = new HashMap<String, Object>();
        processService.complete(taskId, loan.getInstanceId(), loan.getNumber(), loan.getRemark(), "loan", param, request);
        if (saveEntityBoolean) {
            loanService.updateById(loan);
        }
        return ReturnBean.ok("任务已完成");
    }


//    /**
//     * 自动绑定页面字段
//     */
//    @RequestMapping("loan/view/{id}")
//    public String view(@PathVariable(value = "id") Integer id) {
//        request.setAttribute("loan",loanService.selectById(id));
//        return "loan/view";
//    }


    @GetMapping("loan/showFormDialog/{instanceId}")
    public String showFormDialog(@PathVariable("instanceId") String instanceId) {
        String businessKey = processService.findBusinessKeyByInstanceId(instanceId);
        Loan loan = loanService.selectById(new Integer(businessKey));
        request.setAttribute("loan", loan);
        return "gck/loan/view";
    }


}
