package com.jk.gck.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.common.annotation.KrtLog;
import com.jk.common.base.BaseController;
import com.jk.common.bean.DataTable;
import com.jk.common.bean.ReturnBean;
import com.jk.gck.entity.ApprovalRecord;
import com.jk.gck.service.IApprovalRecordService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Map;

/**
 * 审核记录控制层
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年01月22日
 */
@Controller
public class ApprovalRecordController extends BaseController {

    @Autowired
    private IApprovalRecordService approvalRecordService;

    /**
     * 审核记录管理页
     *
     * @return {@link String}
     */
    @RequiresPermissions("ApprovalRecord:approvalRecord:list")
    @GetMapping("gck/approvalRecord/list")
    public String list() {
        return "gck/approvalRecord/list";
    }

    /**
     * 审核记录管理
     *
     * @param para 搜索参数
     * @return {@link DataTable}
     */
    @RequiresPermissions("ApprovalRecord:approvalRecord:list")
    @PostMapping("gck/approvalRecord/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {
        IPage page = approvalRecordService.selectPageList(para);
        return DataTable.ok(page);
    }

    /**
     * 新增审核记录页
     *
     * @return {@link String}
     */
    @RequiresPermissions("ApprovalRecord:approvalRecord:insert")
    @GetMapping("gck/approvalRecord/insert")
    public String insert() {
        return "gck/approvalRecord/insert";
    }

    /**
     * 添加审核记录
     *
     * @param approvalRecord 审核记录
     * @return {@link ReturnBean}
     */
    @KrtLog("添加审核记录")
    @RequiresPermissions("ApprovalRecord:approvalRecord:insert")
    @PostMapping("gck/approvalRecord/insert")
    @ResponseBody
    public ReturnBean insert(ApprovalRecord approvalRecord) {
        approvalRecordService.insert(approvalRecord);
        return ReturnBean.ok();
    }

    /**
     * 修改审核记录页
     *
     * @param id 审核记录id
     * @return {@link String}
     */
    @RequiresPermissions("ApprovalRecord:approvalRecord:update")
    @GetMapping("gck/approvalRecord/update")
    public String update(Integer id) {
        ApprovalRecord approvalRecord = approvalRecordService.selectById(id);
        request.setAttribute("approvalRecord", approvalRecord);
        return "gck/approvalRecord/update";
    }

    /**
     * 修改审核记录
     *
     * @param approvalRecord 审核记录
     * @return {@link ReturnBean}
     */
    @KrtLog("修改审核记录")
    @RequiresPermissions("ApprovalRecord:approvalRecord:update")
    @PostMapping("gck/approvalRecord/update")
    @ResponseBody
    public ReturnBean update(ApprovalRecord approvalRecord) {
        approvalRecordService.updateById(approvalRecord);
        return ReturnBean.ok();
    }

    /**
     * 删除审核记录
     *
     * @param id 审核记录id
     * @return {@link ReturnBean}
     */
    @KrtLog("删除审核记录")
    @RequiresPermissions("ApprovalRecord:approvalRecord:delete")
    @PostMapping("gck/approvalRecord/delete")
    @ResponseBody
    public ReturnBean delete(Integer id) {
        approvalRecordService.deleteById(id);
        return ReturnBean.ok();
    }

    /**
     * 批量删除审核记录
     *
     * @param ids 审核记录ids
     * @return {@link ReturnBean}
     */
    @KrtLog("批量删除审核记录")
    @RequiresPermissions("ApprovalRecord:approvalRecord:delete")
    @PostMapping("gck/approvalRecord/deleteByIds")
    @ResponseBody
    public ReturnBean deleteByIds(Integer[] ids) {
        approvalRecordService.deleteByIds(Arrays.asList(ids));
        return ReturnBean.ok();
    }


}
