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
import com.jk.gck.entity.MainProject;
import com.jk.gck.entity.SubProject;
import com.jk.gck.service.*;
import com.jk.gck.utils.ConstUtils;
import com.jk.sys.entity.Organ;
import com.jk.sys.service.IOrganService;
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

import java.util.*;

/**
 * 合同控制层
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年01月13日
 */
@Controller
public class ContractController extends BaseController {

    @Autowired
    private IContractService contractService;

    @Autowired
    private IMainProjectService mainProjectService;

    @Autowired
    private ISubProjectService subProjectService;

    @Autowired
    private IEntityService iEntityService;

    @Autowired
    private IApprovalRecordService approvalRecordService;

    @Autowired
    private IOrganService iOrganService;



    /**
     * 合同管理页
     *
     * @return {@link String}
     */
    @RequiresPermissions("contract:contract:list")
    @GetMapping("gck/contract/list")
    public String list(@RequestParam(value = "id", required = false, defaultValue = "-1") Long id, @RequestParam(value = "status", required = false, defaultValue = "-1") Long status, @RequestParam(value = "warning", required = false, defaultValue = "-1") Long warning) {

        //主项目
        List<MainProject> mainProjects = mainProjectService.selectList();
        request.setAttribute("mainProjects", mainProjects);

        //甲方
        Map<String, Object> param = new HashedMap<>();
        param.put("is_internal", ConstUtils.ISINTERNAL);
        Collection partyAs = iEntityService.selectByMap(param);
        request.setAttribute("partyAs", partyAs);


        List<Organ> organs = iOrganService.selectByPid(02);


        //乙方
        param.put("is_internal", ConstUtils.ISNOTINTERNAL);
        Collection partyBs = iEntityService.selectByMap(param);
        request.setAttribute("partyBs", partyBs);

        if (id != -1) {  //如果获取到了值
            SubProject subProject = subProjectService.selectById(id);
            request.setAttribute("mainpid", subProject.getMainpid());  //主项目id
            request.setAttribute("projectid", id);  //子项目id
        } else {
            request.setAttribute("projectid", -1);
        }

        if (status != -1) {
            request.setAttribute("status", status + "");
        }

        if (warning != -1) {
            request.setAttribute("warning", warning + "");
        }

        return "gck/contract/list";
    }

    /**
     * 合同管理
     *
     * @param para 搜索参数
     * @return {@link DataTable}
     */
    @RequiresPermissions("contract:contract:list")
    @PostMapping("gck/contract/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {
        //IPage page = contractService.selectPageList(para);
        IPage page = contractService.selectPageMap(para);
        return DataTable.ok(page);
    }

    /**
     * 新增合同页
     *
     * @return {@link String}
     */
    @RequiresPermissions("contract:contract:insert")
    @GetMapping("gck/contract/insert")
    public String insert() {
        //主项目
        List<MainProject> mainProjects = mainProjectService.selectList();
        request.setAttribute("mainProjects", mainProjects);

        //甲方
        Map<String, Object> param = new HashedMap<>();
        param.put("is_internal", ConstUtils.ISINTERNAL);
        Collection partyAs = iEntityService.selectByMap(param);
        request.setAttribute("partyAs", partyAs);

        //乙方
        param.put("is_internal", ConstUtils.ISNOTINTERNAL);
        Collection partyBs = iEntityService.selectByMap(param);
        request.setAttribute("partyBs", partyBs);
        return "gck/contract/insert";
    }

    /**
     * 添加合同
     *
     * @param contract 合同
     * @return {@link ReturnBean}
     */
    @KrtLog("添加合同")
    @RequiresPermissions("contract:contract:insert")
    @PostMapping("gck/contract/insert")
    @ResponseBody
    public ReturnBean insert(Contract contract) {
        contractService.insert(contract);
        return ReturnBean.ok();
    }

    /**
     * 修改合同页
     *
     * @param id 合同id
     * @return {@link String}
     */
    @RequiresPermissions("contract:contract:update")
    @GetMapping("gck/contract/update")
    public String update(Integer id) {
        //主项目
        List<MainProject> mainProjects = mainProjectService.selectList();
        request.setAttribute("mainProjects", mainProjects);
        Contract contract = contractService.selectById(id);
        //子项目
        List<SubProject> subProjects = subProjectService.selectByMainProjectId(contract.getMainprojectid());
        request.setAttribute("subProjects", subProjects);
        //甲方
        Map<String, Object> param = new HashedMap<>();
        param.put("is_internal", ConstUtils.ISINTERNAL);
        Collection partyAs = iEntityService.selectByMap(param);
        request.setAttribute("partyAs", partyAs);

        //乙方
        param.put("is_internal", ConstUtils.ISNOTINTERNAL);
        Collection partyBs = iEntityService.selectByMap(param);
        request.setAttribute("partyBs", partyBs);

        request.setAttribute("contract", contract);
        return "gck/contract/update";
    }

    @RequiresPermissions("contract:contract:list")
    @GetMapping("gck/contract/getSubProject")
    @ResponseBody
    public List<SubProject> getSubProject(Long mainProjectId) {
        List<SubProject> subProjectList = subProjectService.selectByMainProjectId(mainProjectId);
        return subProjectList;
    }

    /**
     * 修改合同
     *
     * @param contract 合同
     * @return {@link ReturnBean}
     */
    @KrtLog("修改合同")
    @RequiresPermissions("contract:contract:update")
    @PostMapping("gck/contract/update")
    @ResponseBody
    public ReturnBean update(Contract contract) {
        contractService.updateById(contract);
        return ReturnBean.ok();
    }

    /**
     * 删除合同
     *
     * @param id 合同id
     * @return {@link ReturnBean}
     */
    @KrtLog("删除合同")
    @RequiresPermissions("contract:contract:delete")
    @PostMapping("gck/contract/delete")
    @ResponseBody
    public ReturnBean delete(Integer id) {
        contractService.deleteById(id);

        return ReturnBean.ok();
    }

    /**
     * 批量删除合同
     *
     * @param ids 合同ids
     * @return {@link ReturnBean}
     */
    @KrtLog("批量删除合同")
    @RequiresPermissions("contract:contract:delete")
    @PostMapping("gck/contract/deleteByIds")
    @ResponseBody
    public ReturnBean deleteByIds(Integer[] ids) {
        contractService.deleteByIds(Arrays.asList(ids));
        return ReturnBean.ok();
    }

    /**
     * 导入合同
     *
     * @param file excel文件
     * @throws Exception 异常
     */
    @KrtLog("导入合同")
    @RequiresPermissions("contract:contract:excelIn")
    @PostMapping("gck/contract/excelIn")
    public void excelIn(@RequestParam("file") MultipartFile file) throws Exception {
        Assert.isExcel(file);
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        params.setNeedSave(false);
        //读取excel
        List<Contract> dataList = ExcelImportUtil.importExcel(file.getInputStream(), Contract.class, params);
        contractService.insertBatch(dataList);
        ServletUtils.printText(response, JSON.toJSONString(ReturnBean.ok()));
    }

    /**
     * 导出合同
     *
     * @param modelMap 返回模型
     * @param para     参数
     */
    @KrtLog("导出合同")
    @RequiresPermissions("contract:contract:excelOut")
    @GetMapping("gck/contract/excelOut")
    public String excelOut(ModelMap modelMap, @RequestParam Map para) {
        List<ExcelExportEntity> entityList = new ArrayList<>();
        entityList.add(new ExcelExportEntity("主项目", "mainProjectName", 15));
        entityList.add(new ExcelExportEntity("子项目", "subProjectName", 15));
        entityList.add(new ExcelExportEntity("合同编号", "number", 15));
        entityList.add(new ExcelExportEntity("合同类型", "contractType", 15));
        entityList.add(new ExcelExportEntity("合同名称", "contractname", 15));
        entityList.add(new ExcelExportEntity("甲方", "partyaName", 15));
        entityList.add(new ExcelExportEntity("乙方", "partybName", 15));
        entityList.add(new ExcelExportEntity("乙方子公司", "partycName", 15));

        entityList.add(new ExcelExportEntity("合同状态", "contractState", 15));
        entityList.add(new ExcelExportEntity("中标价", "price", 15));
        entityList.add(new ExcelExportEntity("审批金额", "approvalsum", 15));
        entityList.add(new ExcelExportEntity("付款金额", "paysum", 15));
        entityList.add(new ExcelExportEntity("发票金额", "billsum", 15));
        entityList.add(new ExcelExportEntity("结算价", "auditprice", 15));
        entityList.add(new ExcelExportEntity("最终结算价", "finalauditprice", 15));

        entityList.add(new ExcelExportEntity("合同签订时间", "signdate", 15));
        entityList.add(new ExcelExportEntity("完工付款比例", "donepayrate", 15));
        entityList.add(new ExcelExportEntity("验收付款比例", "payrate", 15));
        entityList.add(new ExcelExportEntity("质保金比例", "qualitydepositrate", 15));
        entityList.add(new ExcelExportEntity("质保期", "qualityperiod", 15));
        entityList.add(new ExcelExportEntity("竣工时间", "completiondate", 15));
        entityList.add(new ExcelExportEntity("质保期维修费", "qualityfixpay", 15));
        entityList.add(new ExcelExportEntity("备注", "note", 15));
        entityList.add(new ExcelExportEntity("监理中标费率", "supervisionrate", 15));
        entityList.add(new ExcelExportEntity("检测中标费率", "checkrate", 15));
        entityList.add(new ExcelExportEntity("警告信息", "warning", 15));
        entityList.add(new ExcelExportEntity("修改时间", "updateTime", 15));
        List dataResult = contractService.selectExcelList(para);
        modelMap.put(MapExcelConstants.ENTITY_LIST, entityList);
        modelMap.put(MapExcelConstants.MAP_LIST, dataResult);
        modelMap.put(MapExcelConstants.FILE_NAME, "合同");
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("合同", "合同"));
        return MapExcelConstants.EASYPOI_MAP_EXCEL_VIEW;
    }


    @RequiresPermissions("contract:contract:list")
    @GetMapping("gck/contract/select")
    public String select() {
        //主项目
        List<MainProject> mainProjects = mainProjectService.selectList();
        request.setAttribute("mainProjects", mainProjects);

        //甲方
        Map<String, Object> param = new HashedMap<>();
        param.put("is_internal", ConstUtils.ISINTERNAL);
        Collection partyAs = iEntityService.selectByMap(param);
        request.setAttribute("partyAs", partyAs);

        //乙方
        param.put("is_internal", ConstUtils.ISNOTINTERNAL);
        Collection partyBs = iEntityService.selectByMap(param);
        request.setAttribute("partyBs", partyBs);

        return "gck/contract/select";
    }


    /**
     * 主项目统计页
     *
     * @return
     */
    @RequiresPermissions("contract:contract:list")
    @GetMapping("gck/contract/totalPaymentAndFinanaceByMainProject")
    public String totalPaymentAndFinanaceByMainProject() {
        //主项目
        List<MainProject> mainProjects = mainProjectService.selectList();
        request.setAttribute("mainProjects", mainProjects);
        return "gck/contract/totalMainProject";
    }


    /**
     * 主项目统计
     *
     * @param para
     * @return
     */
    @RequiresPermissions("contract:contract:list")
    @PostMapping("gck/contract/totalPaymentAndFinanaceByMainProject")
    @ResponseBody
    public DataTable totalPaymentAndFinanaceByMainProject(@RequestParam Map para) {
        IPage page = contractService.selectAllContractByMainProject(para);
        return DataTable.ok(page);
    }


    /**
     * 主项目统计页
     *
     * @return
     */
    @RequiresPermissions("contract:contract:list")
    @GetMapping("gck/contract/totalByPartyA")
    public String selectContractByPartya() {
        //甲方
        Map<String, Object> param = new HashedMap<>();
        param.put("is_internal", ConstUtils.ISINTERNAL);
        Collection partyAs = iEntityService.selectByMap(param);
        request.setAttribute("partyAs", partyAs);
        return "gck/contract/totalPartyA";
    }


    /**
     * 主项目统计
     *
     * @param para
     * @return
     */
    @RequiresPermissions("contract:contract:list")
    @PostMapping("gck/contract/totalByPartyA")
    @ResponseBody
    public DataTable selectContractByPartya(@RequestParam Map para) {
        IPage page = contractService.selectContractByPartya(para);
        return DataTable.ok(page);
    }

    /**
     * 子项目统计页
     *
     * @return
     */
    @RequiresPermissions("contract:contract:list")
    @GetMapping("gck/contract/totalPaymentAndFinanaceBySubProject")
    public String totalPaymentAndFinanaceBySubProject() {
        //主项目
        List<MainProject> mainProjects = mainProjectService.selectList();
        request.setAttribute("mainProjects", mainProjects);
        return "gck/contract/totalSubProject";
    }


    /**
     * 子项目统计
     *
     * @return
     */
    @RequiresPermissions("contract:contract:list")
    @PostMapping("gck/contract/totalPaymentAndFinanaceBySubProject")
    @ResponseBody
    public DataTable totalPaymentAndFinanaceBySubProject(@RequestParam Map para) {
        //IPage page = contractService.selectPageList(para);
        IPage page = contractService.selectAllContractBySubProject(para);
        return DataTable.ok(page);
    }


    /**
     * 合同统计页
     *
     * @return
     */
    @RequiresPermissions("contract:contract:list")
    @GetMapping("gck/contract/totalPaymentAndFinanaceByContract")
    public String totalPaymentAndFinanaceByContract() {
        //主项目
        List<MainProject> mainProjects = mainProjectService.selectList();
        request.setAttribute("mainProjects", mainProjects);
        return "gck/contract/totalContract";
    }


    /**
     * 合同统计
     *
     * @return
     */
    @RequiresPermissions("contract:contract:list")
    @PostMapping("gck/contract/totalPaymentAndFinanaceByContract")
    @ResponseBody
    public DataTable totalPaymentAndFinanaceByContract(@RequestParam Map para) {
        //IPage page = contractService.selectPageList(para);
        IPage page = contractService.selectAllContractByContract(para);
        return DataTable.ok(page);
    }


    @KrtLog("提交审核")
    @RequiresPermissions("contract:contract:commit")
    @PostMapping("gck/contract/commit")
    @ResponseBody
    public ReturnBean commit(Integer id) {
        Contract contract = contractService.selectContractId(id);
        contract.setStatus(ConstUtils.APPROVALCOMMIT);
        boolean flag = contractService.addCommit(contract);
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
    @RequiresPermissions("contract:contract:check")
    @GetMapping("gck/contract/check")
    public String check(Integer id) {
        Contract contract = contractService.selectById(id);
        request.setAttribute("contract", contract);
        return "gck/contract/check";
    }


    @KrtLog("审核")
    @RequiresPermissions("contract:contract:check")
    @PostMapping("gck/contract/check")
    @ResponseBody
    public ReturnBean check(Contract contract) {
        contractService.addCheck(contract);
        return ReturnBean.ok();
    }


    /**
     * 审核主项目页
     *
     * @param id 主项目id
     * @return {@link String}
     */
    @RequiresPermissions("contract:contract:record")
    @GetMapping("gck/contract/record")
    public String record(Integer id) {
        request.setAttribute("contractId", id);
        return "gck/contract/record";
    }


    /**
     * 审核记录管理
     *
     * @param para 搜索参数
     * @return {@link DataTable}
     */
    @RequiresPermissions("contract:contract:record")
    @PostMapping("gck/contract/record")
    @ResponseBody
    public DataTable record(@RequestParam Map para) {
        IPage page = approvalRecordService.selectPageList(para);
        return DataTable.ok(page);
    }

}
