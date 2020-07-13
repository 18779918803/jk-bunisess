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
import com.jk.gck.entity.FinancialApproval;
import com.jk.gck.service.IApprovalRecordService;
import com.jk.gck.service.IContractService;
import com.jk.gck.service.IEntityService;
import com.jk.gck.service.IFinancialApprovalService;
import com.jk.gck.utils.ConstUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.*;

/**
 * 审批款控制层
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年01月14日
 */
@Controller
public class FinancialApprovalController extends BaseController {

    @Autowired
    private IFinancialApprovalService financialApprovalService;

    @Autowired
    private IContractService iContractService;

    @Autowired
    private IApprovalRecordService approvalRecordService;

    @Autowired
    private IEntityService iEntityService;

    /**
     * 审批款管理页
     *
     * @return {@link String}
     */
    @RequiresPermissions("FinancialApproval:financialApproval:list")
    @GetMapping("gck/financialApproval/list")
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
        return "gck/financialApproval/list";
    }

    /**
     * 审批款管理
     *
     * @param para 搜索参数
     * @return {@link DataTable}
     */
    @RequiresPermissions("FinancialApproval:financialApproval:list")
    @PostMapping("gck/financialApproval/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {
        IPage page = financialApprovalService.selectPageMap(para);
        return DataTable.ok(page);
    }

    /**
     * 新增审批款页
     *
     * @return {@link String}
     */
    @RequiresPermissions("FinancialApproval:financialApproval:insert")
    @GetMapping("gck/financialApproval/insert")
    public String insert(@RequestParam(value = "contractId", required = false, defaultValue = "-1") Integer contractId) {
        Map<String, Object> contract = iContractService.selectByContractId(contractId);
        if (contract != null) {
            request.setAttribute("contract", contract);
        }
        return "gck/financialApproval/insert";
    }

    /**
     * 添加审批款
     *
     * @param financialApproval 审批款
     * @return {@link ReturnBean}
     */
    @KrtLog("添加审批款")
    @RequiresPermissions("FinancialApproval:financialApproval:insert")
    @PostMapping("gck/financialApproval/insert")
    @ResponseBody
    public ReturnBean insert(FinancialApproval financialApproval) {
        financialApprovalService.insert(financialApproval);
        return ReturnBean.ok();
    }

    /**
     * 修改审批款页
     *
     * @param id 审批款id
     * @return {@link String}
     */
    @RequiresPermissions("FinancialApproval:financialApproval:update")
    @GetMapping("gck/financialApproval/update")
    public String update(Integer id) {
        FinancialApproval financialApproval = financialApprovalService.selectById(id);
        Map<String, Object> contract = iContractService.selectMapById(financialApproval.getContractId());
        request.setAttribute("financialApproval", financialApproval);
        request.setAttribute("contract", contract);
        return "gck/financialApproval/update";
    }

    /**
     * 修改审批款
     *
     * @param financialApproval 审批款
     * @return {@link ReturnBean}
     */
    @KrtLog("修改审批款")
    @RequiresPermissions("FinancialApproval:financialApproval:update")
    @PostMapping("gck/financialApproval/update")
    @ResponseBody
    public ReturnBean update(FinancialApproval financialApproval) {
        financialApprovalService.updateById(financialApproval);
        return ReturnBean.ok();
    }

    /**
     * 删除审批款
     *
     * @param id 审批款id
     * @return {@link ReturnBean}
     */
    @KrtLog("删除审批款")
    @RequiresPermissions("FinancialApproval:financialApproval:delete")
    @PostMapping("gck/financialApproval/delete")
    @ResponseBody
    @Transactional
    public ReturnBean delete(Integer id) {

        FinancialApproval financialApproval = financialApprovalService.selectById(id);
        financialApprovalService.deleteById(id);
        Map map = iContractService.selectAmountByContractId(financialApproval.getContractId());
        BigDecimal paySum = (BigDecimal) map.get("paySum");
        BigDecimal approvalSum = (BigDecimal) map.get("approvalSum");
        Contract contract = iContractService.selectById(financialApproval.getContractId());
        if (paySum.compareTo(approvalSum) > 0) {
            contract.setIsWarn(2);
        } else {
            contract.setIsWarn(1);
        }
        iContractService.updateById(contract);
        return ReturnBean.ok();
    }

    /**
     * 批量删除审批款
     *
     * @param ids 审批款ids
     * @return {@link ReturnBean}
     */
    @KrtLog("批量删除审批款")
    @RequiresPermissions("FinancialApproval:financialApproval:delete")
    @PostMapping("gck/financialApproval/deleteByIds")
    @ResponseBody
    public ReturnBean deleteByIds(Integer[] ids) {
        financialApprovalService.deleteByIds(Arrays.asList(ids));
        return ReturnBean.ok();
    }

    /**
     * 导入审批款
     *
     * @param file excel文件
     * @throws Exception 异常
     */
    @KrtLog("导入审批款")
    @RequiresPermissions("FinancialApproval:financialApproval:excelIn")
    @PostMapping("gck/financialApproval/excelIn")
    public void excelIn(@RequestParam("file") MultipartFile file) throws Exception {
        Assert.isExcel(file);
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        params.setNeedSave(false);
        //读取excel
        List<FinancialApproval> dataList = ExcelImportUtil.importExcel(file.getInputStream(), FinancialApproval.class, params);
        financialApprovalService.insertBatch(dataList);
        ServletUtils.printText(response, JSON.toJSONString(ReturnBean.ok()));
    }

    /**
     * 导出审批款
     *
     * @param modelMap 返回模型
     * @param para     参数
     */
    @KrtLog("导出审批款")
    @RequiresPermissions("FinancialApproval:financialApproval:excelOut")
    @GetMapping("gck/financialApproval/excelOut")
    public String excelOut(ModelMap modelMap, Map para) {
        List<ExcelExportEntity> entityList = new ArrayList<>();
        entityList.add(new ExcelExportEntity("合同名称", "contractName", 15));
        entityList.add(new ExcelExportEntity("合同编号", "contractNumber", 15));
        entityList.add(new ExcelExportEntity("甲方", "partyaName", 15));
        entityList.add(new ExcelExportEntity("施工单位", "partybName", 15));
        entityList.add(new ExcelExportEntity("审批编号", "number", 15));
        entityList.add(new ExcelExportEntity("审批金额", "amount", 15));
        entityList.add(new ExcelExportEntity("审批时间", "date", 15));
        entityList.add(new ExcelExportEntity("备注", "note", 15));
        List dataResult = financialApprovalService.selectExcelList(para);
        modelMap.put(MapExcelConstants.ENTITY_LIST, entityList);
        modelMap.put(MapExcelConstants.MAP_LIST, dataResult);
        modelMap.put(MapExcelConstants.FILE_NAME, "审批款");
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("审批款", "审批款"));
        return MapExcelConstants.EASYPOI_MAP_EXCEL_VIEW;
    }


    @KrtLog("提交审核")
    @RequiresPermissions("FinancialApproval:financialApproval:commit")
    @PostMapping("gck/financialApproval/commit")
    @ResponseBody
    public ReturnBean commit(Integer id) {
        FinancialApproval financialApproval = financialApprovalService.selectById(id);
        financialApproval.setStatus(ConstUtils.APPROVALCOMMIT);  //表示的是审核通过
        boolean flag = financialApprovalService.addCommit(financialApproval);
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
    @RequiresPermissions("FinancialApproval:financialApproval:check")
    @GetMapping("gck/financialApproval/check")
    public String check(Integer id) {
        FinancialApproval financialApproval = financialApprovalService.selectById(id);
        request.setAttribute("financialApproval", financialApproval);
        return "gck/financialApproval/check";
    }


    @KrtLog("审核")
    @RequiresPermissions("FinancialApproval:financialApproval:check")
    @PostMapping("gck/financialApproval/check")
    @ResponseBody
    public ReturnBean check(FinancialApproval financialApproval) {
        financialApprovalService.addCheck(financialApproval);
        return ReturnBean.ok();
    }


    /**
     * 审核主项目页
     *
     * @param id 主项目id
     * @return {@link String}
     */
    @RequiresPermissions("FinancialApproval:financialApproval:record")
    @GetMapping("gck/financialApproval/record")
    public String record(Integer id) {
        request.setAttribute("financialApprovalId", id);
        return "gck/financialApproval/record";
    }


    /**
     * 审核记录管理
     *
     * @param para 搜索参数
     * @return {@link DataTable}
     */
    @RequiresPermissions("FinancialApproval:financialApproval:record")
    @PostMapping("gck/financialApproval/record")
    @ResponseBody
    public DataTable record(@RequestParam Map para) {
        IPage page = approvalRecordService.selectPageList(para);
        return DataTable.ok(page);
    }

}
