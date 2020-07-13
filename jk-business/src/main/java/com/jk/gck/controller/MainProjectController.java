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
import com.jk.gck.entity.MainProject;
import com.jk.gck.service.IApprovalRecordService;
import com.jk.gck.service.IMainProjectService;
import com.jk.gck.utils.ConstUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 主项目控制层
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年01月10日
 */
@Controller
public class MainProjectController extends BaseController {

    @Autowired
    private IMainProjectService mainProjectService;

    @Autowired
    private IApprovalRecordService approvalRecordService;

    /**
     * 主项目管理页
     *
     * @return {@link String}
     */
    @RequiresPermissions("MainProject:mainProject:list")
    @GetMapping("gck/mainProject/list")
    public String list(@RequestParam(value = "status", required = false, defaultValue = "-1") Long status) {
        if (status != -1) {
            request.setAttribute("status", status + "");
        }
        return "gck/mainProject/list";
    }

    /**
     * 主项目管理
     *
     * @param para 搜索参数
     * @return {@link DataTable}
     */
    @RequiresPermissions("MainProject:mainProject:list")
    @PostMapping("gck/mainProject/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {
        IPage page = mainProjectService.selectPageList(para);
        return DataTable.ok(page);
    }

    /**
     * 新增主项目页
     *
     * @return {@link String}
     */
    @RequiresPermissions("MainProject:mainProject:insert")
    @GetMapping("gck/mainProject/insert")
    public String insert() {
        return "gck/mainProject/insert";
    }

    /**
     * 添加主项目
     *
     * @param mainProject 主项目
     * @return {@link ReturnBean}
     */
    @KrtLog("添加主项目")
    @RequiresPermissions("MainProject:mainProject:insert")
    @PostMapping("gck/mainProject/insert")
    @ResponseBody
    public ReturnBean insert(MainProject mainProject) {
        mainProjectService.insert(mainProject);
        return ReturnBean.ok();
    }

    /**
     * 修改主项目页
     *
     * @param id 主项目id
     * @return {@link String}
     */
    @RequiresPermissions("MainProject:mainProject:update")
    @GetMapping("gck/mainProject/update")
    public String update(Integer id) {
        MainProject mainProject = mainProjectService.selectById(id);
        request.setAttribute("mainProject", mainProject);
        return "gck/mainProject/update";
    }

    /**
     * 修改主项目
     *
     * @param mainProject 主项目
     * @return {@link ReturnBean}
     */
    @KrtLog("修改主项目")
    @RequiresPermissions("MainProject:mainProject:update")
    @PostMapping("gck/mainProject/update")
    @ResponseBody
    public ReturnBean update(MainProject mainProject) {
        mainProjectService.updateById(mainProject);
        return ReturnBean.ok();
    }

    /**
     * 删除主项目
     *
     * @param id 主项目id
     * @return {@link ReturnBean}
     */
    @KrtLog("删除主项目")
    @RequiresPermissions("MainProject:mainProject:delete")
    @PostMapping("gck/mainProject/delete")
    @ResponseBody
    public ReturnBean delete(Integer id) {
        mainProjectService.deleteById(id);
        return ReturnBean.ok();
    }

    /**
     * 批量删除主项目
     *
     * @param ids 主项目ids
     * @return {@link ReturnBean}
     */
    @KrtLog("批量删除主项目")
    @RequiresPermissions("MainProject:mainProject:delete")
    @PostMapping("gck/mainProject/deleteByIds")
    @ResponseBody
    public ReturnBean deleteByIds(Integer[] ids) {
        mainProjectService.deleteByIds(Arrays.asList(ids));
        return ReturnBean.ok();
    }

    /**
     * 导入主项目
     *
     * @param file excel文件
     * @throws Exception 异常
     */
    @KrtLog("导入主项目")
    @RequiresPermissions("MainProject:mainProject:excelIn")
    @PostMapping("gck/mainProject/excelIn")
    public void excelIn(@RequestParam("file") MultipartFile file) throws Exception {
        Assert.isExcel(file);
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        params.setNeedSave(false);
        //读取excel
        List<MainProject> dataList = ExcelImportUtil.importExcel(file.getInputStream(), MainProject.class, params);
        mainProjectService.insertBatch(dataList);
        ServletUtils.printText(response, JSON.toJSONString(ReturnBean.ok()));
    }

    /**
     * 导出主项目
     *
     * @param modelMap 返回模型
     * @param para     参数
     */
    @KrtLog("导出主项目")
    @RequiresPermissions("MainProject:mainProject:excelOut")
    @GetMapping("gck/mainProject/excelOut")
    public String excelOut(ModelMap modelMap,@RequestParam Map para) {
        List<ExcelExportEntity> entityList = new ArrayList<>();
        /*        entityList.add(new ExcelExportEntity("id", "id", 15));*/
        entityList.add(new ExcelExportEntity("主项目编号", "number", 15));
        entityList.add(new ExcelExportEntity("主项目名字", "name", 15));
        entityList.add(new ExcelExportEntity("备注", "note", 15));
        /* entityList.add(new ExcelExportEntity("状态", "status", 15));*/
        List dataResult = mainProjectService.selectExcelList(para);
        modelMap.put(MapExcelConstants.ENTITY_LIST, entityList);
        modelMap.put(MapExcelConstants.MAP_LIST, dataResult);
        modelMap.put(MapExcelConstants.FILE_NAME, "主项目");
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("主项目", "主项目"));
        return MapExcelConstants.EASYPOI_MAP_EXCEL_VIEW;
    }


    @KrtLog("提交审核")
    @RequiresPermissions("MainProject:mainProject:commit")
    @PostMapping("gck/mainProject/commit")
    @ResponseBody
    public ReturnBean commit(Integer id) {
        MainProject mainProject = mainProjectService.selectById(id);
        mainProject.setStatus(ConstUtils.APPROVALCOMMIT);  //表示的是审核通过
        boolean flag = mainProjectService.addCommit(mainProject);
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
    @RequiresPermissions("MainProject:mainProject:check")
    @GetMapping("gck/mainProject/check")
    public String check(Integer id) {
        MainProject mainProject = mainProjectService.selectById(id);
        request.setAttribute("mainProject", mainProject);
        return "gck/mainProject/check";
    }


    @KrtLog("审核")
    @RequiresPermissions("MainProject:mainProject:check")
    @PostMapping("gck/mainProject/check")
    @ResponseBody
    public ReturnBean check(MainProject mainProject) {
        mainProjectService.addCheck(mainProject);
        return ReturnBean.ok();
    }


    /**
     * 审核主项目页
     *
     * @param id 主项目id
     * @return {@link String}
     */
    @RequiresPermissions("MainProject:mainProject:record")
    @GetMapping("gck/mainProject/record")
    public String record(Integer id) {
        request.setAttribute("mainProjectId", id);
        return "gck/mainProject/record";
    }


    /**
     * 主项目管理
     *
     * @param para 搜索参数
     * @return {@link DataTable}
     */
    @RequiresPermissions("MainProject:mainProject:record")
    @PostMapping("gck/mainProject/record")
    @ResponseBody
    public DataTable record(@RequestParam Map para) {
        IPage page = approvalRecordService.selectPageList(para);
        return DataTable.ok(page);
    }
}
