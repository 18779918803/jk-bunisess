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
import com.jk.gck.entity.Entity;
import com.jk.gck.service.IEntityService;
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
 * 组织控制层
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年01月10日
 */
@Controller
public class EntityController extends BaseController {

    @Autowired
    private IEntityService entityService;

    /**
     * 组织管理页
     *
     * @return {@link String}
     */
    @RequiresPermissions("entity:entity:list")
    @GetMapping("gck/entity/list")
    public String list() {
        return "gck/entity/list";
    }

    /**
     * 组织管理
     *
     * @param para 搜索参数
     * @return {@link DataTable}
     */
    @RequiresPermissions("entity:entity:list")
    @PostMapping("gck/entity/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {
        IPage page = entityService.selectPageList(para);
        return DataTable.ok(page);
    }

    /**
     * 新增组织页
     *
     * @return {@link String}
     */
    @RequiresPermissions("entity:entity:insert")
    @GetMapping("gck/entity/insert")
    public String insert() {
        return "gck/entity/insert";
    }

    /**
     * 添加组织
     *
     * @param entity 组织
     * @return {@link ReturnBean}
     */
    @KrtLog("添加组织")
    @RequiresPermissions("entity:entity:insert")
    @PostMapping("gck/entity/insert")
    @ResponseBody
    public ReturnBean insert(Entity entity) {
        entityService.insert(entity);
        return ReturnBean.ok();
    }

    /**
     * 修改组织页
     *
     * @param id 组织id
     * @return {@link String}
     */
    @RequiresPermissions("entity:entity:update")
    @GetMapping("gck/entity/update")
    public String update(Integer id) {
        Entity entity = entityService.selectById(id);
        request.setAttribute("entity", entity);
        return "gck/entity/update";
    }

    /**
     * 修改组织
     *
     * @param entity 组织
     * @return {@link ReturnBean}
     */
    @KrtLog("修改组织")
    @RequiresPermissions("entity:entity:update")
    @PostMapping("gck/entity/update")
    @ResponseBody
    public ReturnBean update(Entity entity) {
        entityService.updateById(entity);
        return ReturnBean.ok();
    }

    /**
     * 删除组织
     *
     * @param id 组织id
     * @return {@link ReturnBean}
     */
    @KrtLog("删除组织")
    @RequiresPermissions("entity:entity:delete")
    @PostMapping("gck/entity/delete")
    @ResponseBody
    public ReturnBean delete(Integer id) {
        entityService.deleteById(id);
        return ReturnBean.ok();
    }

    /**
     * 批量删除组织
     *
     * @param ids 组织ids
     * @return {@link ReturnBean}
     */
    @KrtLog("批量删除组织")
    @RequiresPermissions("entity:entity:delete")
    @PostMapping("gck/entity/deleteByIds")
    @ResponseBody
    public ReturnBean deleteByIds(Integer[] ids) {
        entityService.deleteByIds(Arrays.asList(ids));
        return ReturnBean.ok();
    }

        /**
        * 导入组织
        *
        * @param file excel文件
        * @throws Exception 异常
        */
        @KrtLog("导入组织")
        @RequiresPermissions("entity:entity:excelIn")
        @PostMapping("gck/entity/excelIn")
        public void excelIn(@RequestParam("file") MultipartFile file) throws Exception {
            Assert.isExcel(file);
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            params.setNeedSave(false);
            //读取excel
            List<Entity> dataList = ExcelImportUtil.importExcel(file.getInputStream(), Entity.class, params);
            entityService.insertBatch(dataList);
            ServletUtils.printText(response, JSON.toJSONString(ReturnBean.ok()));
        }

        /**
        * 导出组织
        *
        * @param modelMap 返回模型
        * @param para 参数
        */
        @KrtLog("导出组织")
        @RequiresPermissions("entity:entity:excelOut")
        @GetMapping("gck/entity/excelOut")
        public String excelOut(ModelMap modelMap,Map para) {
            List<ExcelExportEntity> entityList = new ArrayList<>();
                entityList.add(new ExcelExportEntity("组织名称", "name", 15));
                entityList.add(new ExcelExportEntity("编号", "creditCode", 15));
                entityList.add(new ExcelExportEntity("地址", "address", 15));
                entityList.add(new ExcelExportEntity("银行账户", "bankAccount", 15));
                entityList.add(new ExcelExportEntity("备注", "note", 15));
                entityList.add(new ExcelExportEntity("是否是内部组织", "isInternal", 15));
                entityList.add(new ExcelExportEntity("状态", "status", 15));
            List dataResult = entityService.selectExcelList(para);
            modelMap.put(MapExcelConstants.ENTITY_LIST, entityList);
            modelMap.put(MapExcelConstants.MAP_LIST, dataResult);
            modelMap.put(MapExcelConstants.FILE_NAME, "组织");
            modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("组织", "组织"));
            return MapExcelConstants.EASYPOI_MAP_EXCEL_VIEW;
        }
}
