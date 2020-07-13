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
import com.jk.gck.entity.SubProject;
import com.jk.gck.service.IApprovalRecordService;
import com.jk.gck.service.IEntityService;
import com.jk.gck.service.IMainProjectService;
import com.jk.gck.service.ISubProjectService;
import com.jk.gck.utils.ConstUtils;
import com.jk.sys.service.IDicService;
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
 * 子项目控制层
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年01月10日
 */
@Controller
public class SubProjectController extends BaseController {

    @Autowired
    private ISubProjectService subProjectService;

    @Autowired
    private IMainProjectService mainProjectService;

    @Autowired
    private IEntityService entityService;

    @Autowired
    private IApprovalRecordService approvalRecordService;

    @Autowired
    private IDicService dicService;


    /**
     * 子项目管理页
     *
     * @return {@link String}
     */
    @RequiresPermissions("Subject:subProject:list")
    @GetMapping("gck/subProject/list")
    public String list(@RequestParam(value = "id", required = false, defaultValue ="-1") Long id,@RequestParam(value = "status", required = false, defaultValue ="-1") Long status) {

       //获取组织
        Map<String, Object> param = new HashedMap<>();
        param.put("is_internal", ConstUtils.ISINTERNAL);
        Collection entitys = entityService.selectByMap(param);
        request.setAttribute("entitys", entitys);

        if(status!=-1){
            request.setAttribute("status",status+"");
        }

        //主项目
        List<MainProject> mainProjects = mainProjectService.selectList();
        request.setAttribute("mainProjects", mainProjects);
        if(id!=-1){  //如果获取到了值
            request.setAttribute("mainpid",id);
        }
        return "gck/subProject/list";
    }

    /**
     * 子项目管理
     *
     * @param para 搜索参数
     * @return {@link DataTable}
     */
    @RequiresPermissions("Subject:subProject:list")
    @PostMapping("gck/subProject/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {
        IPage page = subProjectService.selectPageMap(para);
        return DataTable.ok(page);
    }

    /**
     * 新增子项目页
     *
     * @return {@link String}
     */
    @RequiresPermissions("Subject:subProject:insert")
    @GetMapping("gck/subProject/insert")
    public String insert() {

        List<MainProject> mainProjects = mainProjectService.selectList();
        request.setAttribute("mainProjects", mainProjects);
        Map<String, Object> param = new HashedMap<>();
        param.put("is_internal", ConstUtils.ISINTERNAL);
        Collection entitys = entityService.selectByMap(param);
        request.setAttribute("entitys", entitys);

        return "gck/subProject/insert";
    }

    /**
     * 添加子项目
     *
     * @param subProject 子项目
     * @return {@link ReturnBean}
     */
    @KrtLog("添加子项目")
    @RequiresPermissions("Subject:subProject:insert")
    @PostMapping("gck/subProject/insert")
    @ResponseBody
    public ReturnBean insert(SubProject subProject) {
        subProjectService.insert(subProject);
        return ReturnBean.ok();
    }

    /**
     * 修改子项目页
     *
     * @param id 子项目id
     * @return {@link String}
     */
    @RequiresPermissions("Subject:subProject:update")
    @GetMapping("gck/subProject/update")
    public String update(Integer id) {

        List<MainProject> mainProjects = mainProjectService.selectList();
        request.setAttribute("mainProjects", mainProjects);
        Map<String, Object> param = new HashedMap<>();
        param.put("is_internal", ConstUtils.ISINTERNAL);
        Collection entitys = entityService.selectByMap(param);
        request.setAttribute("entitys", entitys);

        SubProject subProject = subProjectService.selectById(id);
        request.setAttribute("subProject", subProject);
        return "gck/subProject/update";
    }

    /**
     * 修改子项目
     *
     * @param subProject 子项目
     * @return {@link ReturnBean}
     */
    @KrtLog("修改子项目")
    @RequiresPermissions("Subject:subProject:update")
    @PostMapping("gck/subProject/update")
    @ResponseBody
    public ReturnBean update(SubProject subProject) {
        subProjectService.updateById(subProject);
        return ReturnBean.ok();
    }

    /**
     * 删除子项目
     *
     * @param id 子项目id
     * @return {@link ReturnBean}
     */
    @KrtLog("删除子项目")
    @RequiresPermissions("Subject:subProject:delete")
    @PostMapping("gck/subProject/delete")
    @ResponseBody
    public ReturnBean delete(Integer id) {
        subProjectService.deleteById(id);
        return ReturnBean.ok();
    }

    /**
     * 批量删除子项目
     *
     * @param ids 子项目ids
     * @return {@link ReturnBean}
     */
    @KrtLog("批量删除子项目")
    @RequiresPermissions("Subject:subProject:delete")
    @PostMapping("gck/subProject/deleteByIds")
    @ResponseBody
    public ReturnBean deleteByIds(Integer[] ids) {
        subProjectService.deleteByIds(Arrays.asList(ids));
        return ReturnBean.ok();
    }

    /**
     * 导入子项目
     *
     * @param file excel文件
     * @throws Exception 异常
     */
    @KrtLog("导入子项目")
    @RequiresPermissions("Subject:subProject:excelIn")
    @PostMapping("gck/subProject/excelIn")
    public void excelIn(@RequestParam("file") MultipartFile file) throws Exception {
        Assert.isExcel(file);
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        params.setNeedSave(false);
        //读取excel
        List<SubProject> dataList = ExcelImportUtil.importExcel(file.getInputStream(), SubProject.class, params);
        subProjectService.insertBatch(dataList);
        ServletUtils.printText(response, JSON.toJSONString(ReturnBean.ok()));
    }

    /**
     * 导出子项目
     *
     * @param modelMap 返回模型
     * @param para     参数
     */
    @KrtLog("导出子项目")
    @RequiresPermissions("Subject:subProject:excelOut")
    @GetMapping("gck/subProject/excelOut")
    public String excelOut(ModelMap modelMap, Map para) {
        List<ExcelExportEntity> entityList = new ArrayList<>();
        entityList.add(new ExcelExportEntity("子项目编号", "number", 15));
        entityList.add(new ExcelExportEntity("子项目名称", "name", 15));
        entityList.add(new ExcelExportEntity("主项目名称", "mainProjectName", 15));
        entityList.add(new ExcelExportEntity("组织名称", "entityName", 15));
        entityList.add(new ExcelExportEntity("子项目类型", "subProjectType", 15));
        entityList.add(new ExcelExportEntity("描述", "des", 15));
        entityList.add(new ExcelExportEntity("备注", "note", 15));

        List dataResult = subProjectService.selectExcelList(para);
        modelMap.put(MapExcelConstants.ENTITY_LIST, entityList);
        modelMap.put(MapExcelConstants.MAP_LIST, dataResult);
        modelMap.put(MapExcelConstants.FILE_NAME, "子项目");
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("子项目", "子项目"));
        return MapExcelConstants.EASYPOI_MAP_EXCEL_VIEW;
    }


    @KrtLog("提交审核")
    @RequiresPermissions("Subject:subProject:commit")
    @PostMapping("gck/subProject/commit")
    @ResponseBody
    public ReturnBean commit(Integer id) {
        SubProject subProject = subProjectService.selectById(id);
        subProject.setStatus(ConstUtils.APPROVALCOMMIT);  //表示的是审核通过
        boolean  flag=subProjectService.addCommit(subProject);
        if(flag){
            return ReturnBean.ok();
        }else{
            return ReturnBean.error();
        }
    }


    /**
     * 审核主项目页
     *
     * @param id 主项目id
     * @return {@link String}
     */
    @RequiresPermissions("Subject:subProject:check")
    @GetMapping("gck/subProject/check")
    public String check(Integer id) {
        SubProject subProject = subProjectService.selectById(id);
        request.setAttribute("subProject", subProject);
        return "gck/subProject/check";
    }


    @KrtLog("审核")
    @RequiresPermissions("Subject:subProject:check")
    @PostMapping("gck/subProject/check")
    @ResponseBody
    public ReturnBean check(SubProject subProject) {
        subProjectService.addCheck(subProject);
        return ReturnBean.ok();
    }


    /**
     * 审核主项目页
     *
     * @param id 主项目id
     * @return {@link String}
     */
    @RequiresPermissions("Subject:subProject:record")
    @GetMapping("gck/subProject/record")
    public String record(Integer id) {
        request.setAttribute("subProjectId",id);
        return "gck/subProject/record";
    }


    /**
     * 主项目管理
     *
     * @param para 搜索参数
     * @return {@link DataTable}
     */
    @RequiresPermissions("Subject:subProject:record")
    @PostMapping("gck/subProject/record")
    @ResponseBody
    public DataTable record(@RequestParam Map para) {
        IPage page = approvalRecordService.selectPageList(para);
        return DataTable.ok(page);
    }
}
