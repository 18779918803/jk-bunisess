package com.jk.gck.controller;

import cn.afterturn.easypoi.entity.vo.MapExcelConstants;
import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.common.annotation.KrtLog;
import com.jk.common.base.BaseController;
import com.jk.common.bean.DataTable;
import com.jk.common.bean.ReturnBean;
import com.jk.gck.entity.BankOrgan;
import com.jk.gck.service.IBankOrganService;
import com.jk.sys.entity.Organ;
import com.jk.sys.service.IOrganService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 公司开户信息控制层
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年06月15日
 */
@Controller
public class BankOrganController extends BaseController {

    @Autowired
    private IBankOrganService bankOrganService;


    @Autowired
    private IOrganService organService;

    /**
     * 公司开户信息管理页
     *
     * @return {@link String}
     */
    @RequiresPermissions("BankOrgan:bankOrgan:list")
    @GetMapping("gck/bankOrgan/list")
    public String list() {
        List<Organ> organList = organService.selectList();
        request.setAttribute("organList", organList);
        return "gck/bankOrgan/list";
    }

    /**
     * 公司开户信息管理
     *
     * @param para 搜索参数
     * @return {@link DataTable}
     */
    @RequiresPermissions("BankOrgan:bankOrgan:list")
    @PostMapping("gck/bankOrgan/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {
        IPage page = bankOrganService.selectPageMap(para);
        return DataTable.ok(page);
    }

    /**
     * 新增公司开户信息页
     *
     * @return {@link String}
     */
    @RequiresPermissions("BankOrgan:bankOrgan:insert")
    @GetMapping("gck/bankOrgan/insert")
    public String insert() {
        List<Organ> organList = organService.selectList();
        request.setAttribute("organList", organList);
        return "gck/bankOrgan/insert";
    }

    /**
     * 添加公司开户信息
     *
     * @param bankOrgan 公司开户信息
     * @return {@link ReturnBean}
     */
    @KrtLog("添加公司开户信息")
    @RequiresPermissions("BankOrgan:bankOrgan:insert")
    @PostMapping("gck/bankOrgan/insert")
    @ResponseBody
    public ReturnBean insert(BankOrgan bankOrgan) {
        bankOrganService.insert(bankOrgan);
        return ReturnBean.ok();
    }

    /**
     * 修改公司开户信息页
     *
     * @param id 公司开户信息id
     * @return {@link String}
     */
    @RequiresPermissions("BankOrgan:bankOrgan:update")
    @GetMapping("gck/bankOrgan/update")
    public String update(Integer id) {
        List<Organ> organList = organService.selectList();
        BankOrgan bankOrgan = bankOrganService.selectById(id);
        request.setAttribute("organList", organList);
        request.setAttribute("bankOrgan", bankOrgan);
        return "gck/bankOrgan/update";
    }

    /**
     * 修改公司开户信息
     *
     * @param bankOrgan 公司开户信息
     * @return {@link ReturnBean}
     */
    @KrtLog("修改公司开户信息")
    @RequiresPermissions("BankOrgan:bankOrgan:update")
    @PostMapping("gck/bankOrgan/update")
    @ResponseBody
    public ReturnBean update(BankOrgan bankOrgan) {
        bankOrganService.updateById(bankOrgan);
        return ReturnBean.ok();
    }

    /**
     * 删除公司开户信息
     *
     * @param id 公司开户信息id
     * @return {@link ReturnBean}
     */
    @KrtLog("删除公司开户信息")
    @RequiresPermissions("BankOrgan:bankOrgan:delete")
    @PostMapping("gck/bankOrgan/delete")
    @ResponseBody
    public ReturnBean delete(Integer id) {
        bankOrganService.deleteById(id);
        return ReturnBean.ok();
    }

    /**
     * 批量删除公司开户信息
     *
     * @param ids 公司开户信息ids
     * @return {@link ReturnBean}
     */
    @KrtLog("批量删除公司开户信息")
    @RequiresPermissions("BankOrgan:bankOrgan:delete")
    @PostMapping("gck/bankOrgan/deleteByIds")
    @ResponseBody
    public ReturnBean deleteByIds(Integer[] ids) {
        bankOrganService.deleteByIds(Arrays.asList(ids));
        return ReturnBean.ok();
    }


    /**
     * 导出公司开户信息
     *
     * @param modelMap 返回模型
     * @param para     参数
     */
    @KrtLog("导出公司开户信息")
    @RequiresPermissions("BankOrgan:bankOrgan:excelOut")
    @GetMapping("gck/bankOrgan/excelOut")
    public String excelOut(ModelMap modelMap, Map para) {
        List<ExcelExportEntity> entityList = new ArrayList<>();
        entityList.add(new ExcelExportEntity("公司名称", "organId", 15));
        entityList.add(new ExcelExportEntity("户名", "userName", 15));
        entityList.add(new ExcelExportEntity("开户行", "bankName", 15));
        entityList.add(new ExcelExportEntity("开户账号", "bankAccount", 15));
        List dataResult = bankOrganService.selectExcelList(para);
        modelMap.put(MapExcelConstants.ENTITY_LIST, entityList);
        modelMap.put(MapExcelConstants.MAP_LIST, dataResult);
        modelMap.put(MapExcelConstants.FILE_NAME, "公司开户信息");
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("公司开户信息", "公司开户信息"));
        return MapExcelConstants.EASYPOI_MAP_EXCEL_VIEW;
    }

    @KrtLog("获取指定组织的银行信息")
    @RequiresPermissions("BankOrgan:bankOrgan:list")
    @GetMapping("gck/bankOrgan/getListByOrganId")
    @ResponseBody
    List<BankOrgan> getListByOrganId(Integer organId){
         return bankOrganService.selectListByOrganId(organId);
    }
}
