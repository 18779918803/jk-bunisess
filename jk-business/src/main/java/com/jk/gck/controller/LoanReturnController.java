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
import com.jk.gck.entity.Loan;
import com.jk.gck.entity.LoanReturn;
import com.jk.gck.service.ILoanReturnService;
import com.jk.gck.service.ILoanService;
import com.jk.gck.utils.ConstUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
 * 归还本金和利息控制层
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年05月13日
 */
@Controller
public class LoanReturnController extends BaseController {

    @Autowired
    private ILoanReturnService loanReturnService;

    @Autowired
    private ILoanService loanService;

    /**
     * 归还本金和利息管理页
     *
     * @return {@link String}
     */
    @RequiresPermissions("LoanReturn:loanReturn:list")
    @GetMapping("gck/loanReturn/list")
    public String list(@RequestParam(value = "loanId", required = false, defaultValue ="-1") Long loanId) {
        List<Loan> loans = loanService.selectList();
        if(loanId!=-1){  //如果获取到了值
            request.setAttribute("loanId",loanId);
        }
        request.setAttribute("loans",loans);
        return "gck/loanReturn/list";
    }

    /**
     * 归还本金和利息管理
     *
     * @param para 搜索参数
     * @return {@link DataTable}
     */
    @RequiresPermissions("LoanReturn:loanReturn:list")
    @PostMapping("gck/loanReturn/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {
        IPage page = loanReturnService.selectPageMap(para);
        return DataTable.ok(page);
    }

    /**
     * 新增归还本金和利息页
     *
     * @return {@link String}
     */
    @RequiresPermissions("LoanReturn:loanReturn:insert")
    @GetMapping("gck/loanReturn/insert")
    public String insert() {
        return "gck/loanReturn/insert";
    }

    /**
     * 添加归还本金和利息
     *
     * @param loanReturn 归还本金和利息
     * @return {@link ReturnBean}
     */
    @KrtLog("添加归还本金和利息")
    @RequiresPermissions("LoanReturn:loanReturn:insert")
    @PostMapping("gck/loanReturn/insert")
    @ResponseBody
    @Transactional
    public ReturnBean insert(LoanReturn loanReturn) {
        loanReturn.setStatus(ConstUtils.APPROVALUNCOMMIT);
        Loan loan= loanService.selectById(loanReturn.getLoanId());
        if(loanReturn.getAmount().compareTo(loanReturn.getShouldAmount())>=0&&loanReturn.getRate().compareTo(loanReturn.getShouldRate())>=0){
            loan.setState(ConstUtils.APPROVALUNCOMMIT);  //表示的是缴清
            loanService.updateById(loan);
        }
        loanReturnService.insert(loanReturn);
        return ReturnBean.ok();
    }

    /**
     * 修改归还本金和利息页
     *
     * @param id 归还本金和利息id
     * @return {@link String}
     */
    @RequiresPermissions("LoanReturn:loanReturn:update")
    @GetMapping("gck/loanReturn/update")
    public String update(Integer id) {
        LoanReturn loanReturn = loanReturnService.selectById(id);
        request.setAttribute("loanReturn", loanReturn);
        return "gck/loanReturn/update";
    }

    /**
     * 修改归还本金和利息
     *
     * @param loanReturn 归还本金和利息
     * @return {@link ReturnBean}
     */
    @KrtLog("修改归还本金和利息")
    @RequiresPermissions("LoanReturn:loanReturn:update")
    @PostMapping("gck/loanReturn/update")
    @ResponseBody
    public ReturnBean update(LoanReturn loanReturn) {
        loanReturnService.updateById(loanReturn);
        return ReturnBean.ok();
    }

    /**
     * 删除归还本金和利息
     *
     * @param id 归还本金和利息id
     * @return {@link ReturnBean}
     */
    @KrtLog("删除归还本金和利息")
    @RequiresPermissions("LoanReturn:loanReturn:delete")
    @PostMapping("gck/loanReturn/delete")
    @ResponseBody
    public ReturnBean delete(Integer id) {
        loanReturnService.deleteById(id);
        return ReturnBean.ok();
    }

    /**
     * 批量删除归还本金和利息
     *
     * @param ids 归还本金和利息ids
     * @return {@link ReturnBean}
     */
    @KrtLog("批量删除归还本金和利息")
    @RequiresPermissions("LoanReturn:loanReturn:delete")
    @PostMapping("gck/loanReturn/deleteByIds")
    @ResponseBody
    public ReturnBean deleteByIds(Integer[] ids) {
        loanReturnService.deleteByIds(Arrays.asList(ids));
        return ReturnBean.ok();
    }


    /**
     * 导出归还本金和利息
     *
     * @param modelMap 返回模型
     * @param para     参数
     */
    @KrtLog("导出归还本金和利息")
    @RequiresPermissions("LoanReturn:loanReturn:excelOut")
    @GetMapping("gck/loanReturn/excelOut")
    public String excelOut(ModelMap modelMap, Map para) {
        List<ExcelExportEntity> entityList = new ArrayList<>();
        entityList.add(new ExcelExportEntity("借款协议编号", "loanNumber", 15));
        entityList.add(new ExcelExportEntity("应还本金", "shouldAmount", 15));
        entityList.add(new ExcelExportEntity("已还本金", "amount", 15));
        entityList.add(new ExcelExportEntity("应还利息", "shouldRate", 15));
        entityList.add(new ExcelExportEntity("已还利息", "rate", 15));
        entityList.add(new ExcelExportEntity("还款时间", "tradeTime", 15));
        entityList.add(new ExcelExportEntity("备注", "remark", 15));
        List dataResult = loanReturnService.selectExcelList(para);
        modelMap.put(MapExcelConstants.ENTITY_LIST, entityList);
        modelMap.put(MapExcelConstants.MAP_LIST, dataResult);
        modelMap.put(MapExcelConstants.FILE_NAME, "归还本金和利息");
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("归还本金和利息", "归还本金和利息"));
        return MapExcelConstants.EASYPOI_MAP_EXCEL_VIEW;
    }
}
