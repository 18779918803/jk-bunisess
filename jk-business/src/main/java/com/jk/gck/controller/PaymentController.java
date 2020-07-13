package com.jk.gck.controller;

import cn.afterturn.easypoi.entity.vo.MapExcelConstants;
import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.common.annotation.KrtLog;
import com.jk.common.base.BaseController;
import com.jk.common.bean.DataTable;
import com.jk.common.bean.ReturnBean;
import com.jk.common.util.ServletUtils;
import com.jk.common.validator.Assert;
import com.jk.gck.entity.Contract;
import com.jk.gck.entity.Payment;
import com.jk.gck.service.IApprovalRecordService;
import com.jk.gck.service.IContractService;
import com.jk.gck.service.IEntityService;
import com.jk.gck.service.IPaymentService;
import com.jk.gck.utils.ConstUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.*;

/**
 * 付款控制层
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年01月14日
 */
@Controller
public class PaymentController extends BaseController {

    @Autowired
    private IPaymentService paymentService;
    @Autowired
    private IContractService contractService;
    @Autowired
    private IApprovalRecordService approvalRecordService;
    @Autowired
    private IEntityService iEntityService;

    /**
     * 付款管理页
     *
     * @return {@link String}
     */
    @RequiresPermissions("Payment:payment:list")
    @GetMapping("gck/payment/list")
    public String list(@RequestParam(value = "id", required = false, defaultValue = "-1") Long id, @RequestParam(value = "status", required = false, defaultValue = "-1") Long status) {
        if (id != -1) {  //表示的是传递过来合同的id
            request.setAttribute("contractId", id);
        }

        if (status != -1) {
            request.setAttribute("status", status + "");
        }
        //甲方
        Map<String, Object> param = new HashedMap<>();
        param.put("is_internal", ConstUtils.ISINTERNAL);
        Collection partyAs = iEntityService.selectByMap(param);
        request.setAttribute("partyAs", partyAs);

        //乙方
        param.put("is_internal", ConstUtils.ISNOTINTERNAL);
        Collection partyBs = iEntityService.selectByMap(param);
        request.setAttribute("partyBs", partyBs);
        return "gck/payment/list";
    }

    /**
     * 付款管理
     *
     * @param para 搜索参数
     * @return {@link DataTable}
     */
    @RequiresPermissions("Payment:payment:list")
    @PostMapping("gck/payment/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {

        IPage page = paymentService.selectPageMap(para);
        return DataTable.ok(page);
    }


    /**
     * 新增付款页
     *
     * @return {@link String}
     */
    @RequiresPermissions("Payment:payment:insert")
    @GetMapping("gck/payment/insert")
    public String insert(@RequestParam(value = "contractId", required = false, defaultValue = "-1") Integer contractId) {
        Map<String,Object> contract = contractService.selectByContractId(contractId);
        if (contract !=null) {
            request.setAttribute("contract",contract);
        }
        return "gck/payment/insert";
    }

    /**
     * 添加付款
     *
     * @param payment 付款
     * @return {@link ReturnBean}
     */
    @KrtLog("添加付款")
    @RequiresPermissions("Payment:payment:insert")
    @PostMapping("gck/payment/insert")
    @ResponseBody
    public ReturnBean insert(Payment payment) {
        paymentService.insert(payment);
        return ReturnBean.ok();
    }

    /**
     * 修改付款页
     *
     * @param id 付款id
     * @return {@link String}
     */
    @RequiresPermissions("Payment:payment:update")
    @GetMapping("gck/payment/update")
    public String update(Integer id) {
        Payment payment = paymentService.selectById(id);
        Map<String, Object> contract = contractService.selectMapById(payment.getContractId());
        request.setAttribute("contract", contract);
        request.setAttribute("payment", payment);
        return "gck/payment/update";
    }

    /**
     * 修改付款
     *
     * @param payment 付款
     * @return {@link ReturnBean}
     */
    @KrtLog("修改付款")
    @RequiresPermissions("Payment:payment:update")
    @PostMapping("gck/payment/update")
    @ResponseBody
    public ReturnBean update(Payment payment) {
        paymentService.updateById(payment);
        return ReturnBean.ok();
    }

    /**
     * 删除付款
     *
     * @param id 付款id
     * @return {@link ReturnBean}
     */
    @KrtLog("删除付款")
    @RequiresPermissions("Payment:payment:delete")
    @PostMapping("gck/payment/delete")
    @ResponseBody
    public ReturnBean delete(Integer id) {

        Payment payment = paymentService.selectById(id);
        paymentService.deleteById(id);
        Map map = contractService.selectAmountByContractId(payment.getContractId());
        BigDecimal paySum = (BigDecimal) map.get("paySum");
        BigDecimal approvalSum = (BigDecimal) map.get("approvalSum");
        Contract contract = contractService.selectById(payment.getContractId());
        if (paySum.compareTo(approvalSum) > 0) {
            contract.setIsWarn(2);
        } else {
            contract.setIsWarn(1);
        }
        contractService.updateById(contract);
        return ReturnBean.ok();
    }

    /**
     * 批量删除付款
     *
     * @param ids 付款ids
     * @return {@link ReturnBean}
     */
    @KrtLog("批量删除付款")
    @RequiresPermissions("Payment:payment:delete")
    @PostMapping("gck/payment/deleteByIds")
    @ResponseBody
    public ReturnBean deleteByIds(Integer[] ids) {
        paymentService.deleteByIds(Arrays.asList(ids));
        return ReturnBean.ok();
    }

    /**
     * 导入付款
     *
     * @param file excel文件
     * @throws Exception 异常
     */
    @KrtLog("导入付款")
    @RequiresPermissions("Payment:payment:excelIn")
    @PostMapping("gck/payment/excelIn")
    public void excelIn(@RequestParam("file") MultipartFile file) throws Exception {
        Assert.isExcel(file);
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        params.setNeedSave(false);
        //读取excel
        List<Payment> dataList = ExcelImportUtil.importExcel(file.getInputStream(), Payment.class, params);
        paymentService.insertBatch(dataList);
        ServletUtils.printText(response, JSON.toJSONString(ReturnBean.ok()));
    }

    /**
     * 导出付款
     *
     * @param modelMap 返回模型
     * @param para     参数
     */
    @KrtLog("导出付款")
    @RequiresPermissions("Payment:payment:excelOut")
    @GetMapping("gck/payment/excelOut")
    public String excelOut(ModelMap modelMap, @RequestParam Map para) {
        List<ExcelExportEntity> entityList = new ArrayList<>();
        entityList.add(new ExcelExportEntity("合同名称", "contractName", 15));
        entityList.add(new ExcelExportEntity("合同编号", "contractNumber", 15));
        entityList.add(new ExcelExportEntity("甲方", "partyaName", 15));
        entityList.add(new ExcelExportEntity("施工单位", "partybName", 15));
        entityList.add(new ExcelExportEntity("付款凭证号", "number", 15));
        entityList.add(new ExcelExportEntity("应付", "amount", 15));
        entityList.add(new ExcelExportEntity("实付", "shouldAmount", 15));
        entityList.add(new ExcelExportEntity("扣款", "cutAmount", 15));
        entityList.add(new ExcelExportEntity("发票金额", "billAmount", 15));
        entityList.add(new ExcelExportEntity("扣款说明", "cutNote", 15));
        entityList.add(new ExcelExportEntity("交易时间", "tradeTime", 15));
        entityList.add(new ExcelExportEntity("备注", "note", 15));
        List dataResult = paymentService.selectExcelList(para);
        modelMap.put(MapExcelConstants.ENTITY_LIST, entityList);
        modelMap.put(MapExcelConstants.MAP_LIST, dataResult);
        modelMap.put(MapExcelConstants.FILE_NAME, "付款");
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("付款", "付款"));
        return MapExcelConstants.EASYPOI_MAP_EXCEL_VIEW;
    }


    @KrtLog("提交审核")
    @RequiresPermissions("Payment:payment:commit")
    @PostMapping("gck/payment/commit")
    @ResponseBody
    public ReturnBean commit(Integer id) {
        Payment payment = paymentService.selectById(id);
        payment.setStatus(ConstUtils.APPROVALCOMMIT);  //表示的是审核通过
        boolean flag = paymentService.addCommit(payment);
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
    @RequiresPermissions("Payment:payment:check")
    @GetMapping("gck/payment/check")
    public String check(Integer id) {
        Payment payment = paymentService.selectById(id);
        request.setAttribute("payment", payment);
        return "gck/payment/check";
    }


    @KrtLog("审核")
    @RequiresPermissions("Payment:payment:check")
    @PostMapping("gck/payment/check")
    @ResponseBody
    public ReturnBean check(Payment payment) {
        paymentService.addCheck(payment);
        return ReturnBean.ok();
    }


    /**
     * 审核主项目页
     *
     * @param id 主项目id
     * @return {@link String}
     */
    @RequiresPermissions("Payment:payment:record")
    @GetMapping("gck/payment/record")
    public String record(Integer id) {
        request.setAttribute("paymentId", id);
        return "gck/payment/record";
    }


    /**
     * 审核记录管理
     *
     * @param para 搜索参数
     * @return {@link DataTable}
     */
    @RequiresPermissions("Payment:payment:record")
    @PostMapping("gck/payment/record")
    @ResponseBody
    public DataTable record(@RequestParam Map para) {
        IPage page = approvalRecordService.selectPageList(para);
        return DataTable.ok(page);
    }
}
