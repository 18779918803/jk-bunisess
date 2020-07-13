package com.jk.gck.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jk.common.annotation.KrtLog;
import com.jk.common.base.BaseController;
import com.jk.common.bean.DataTable;
import com.jk.common.bean.ReturnBean;
import com.jk.gck.entity.UserOrgan;
import com.jk.gck.service.IUserOrganService;
import com.jk.sys.entity.Organ;
import com.jk.sys.entity.User;
import com.jk.sys.service.IOrganService;
import com.jk.sys.service.IUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 用户关联组织控制层
 *
 * @author 晏攀林
 * @version 1.0
 * @date 2020年06月11日
 */
@Controller
public class UserOrganController extends BaseController {

    @Autowired
    private IUserOrganService userOrganService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IOrganService organService;


    /**
     * 用户关联组织管理页
     *
     * @return {@link String}
     */
    @RequiresPermissions("UserOrgan:userOrgan:list")
    @GetMapping("gck/userOrgan/list")
    public String list() {
        List<User> userList = userService.selectList();
        List<Organ> organList = organService.selectList();
        request.setAttribute("userList",userList);
        request.setAttribute("organList",organList);
        return "gck/userOrgan/list";
    }

    /**
     * 用户关联组织管理
     *
     * @param para 搜索参数
     * @return {@link DataTable}
     */
    @RequiresPermissions("UserOrgan:userOrgan:list")
    @PostMapping("gck/userOrgan/list")
    @ResponseBody
    public DataTable list(@RequestParam Map para) {
        IPage page = userOrganService.selectPageMap(para);
        return DataTable.ok(page);
    }

    /**
     * 新增用户关联组织页
     *
     * @return {@link String}
     */
    @RequiresPermissions("UserOrgan:userOrgan:insert")
    @GetMapping("gck/userOrgan/insert")
    public String insert() {
        List<User> userList = userService.selectList();
        List<Organ> organList = organService.selectList();
        request.setAttribute("userList",userList);
        request.setAttribute("organList",organList);
        return "gck/userOrgan/insert";
    }

    /**
     * 添加用户关联组织
     *
     * @param userOrgan 用户关联组织
     * @return {@link ReturnBean}
     */
    @KrtLog("添加用户关联组织")
    @RequiresPermissions("UserOrgan:userOrgan:insert")
    @PostMapping("gck/userOrgan/insert")
    @ResponseBody
    public ReturnBean insert(UserOrgan userOrgan) {
        userOrganService.insert(userOrgan);
        return ReturnBean.ok();
    }

    /**
     * 修改用户关联组织页
     *
     * @param id 用户关联组织id
     * @return {@link String}
     */
    @RequiresPermissions("UserOrgan:userOrgan:update")
    @GetMapping("gck/userOrgan/update")
    public String update(Integer id) {
        List<User> userList = userService.selectList();
        List<Organ> organList = organService.selectList();
        request.setAttribute("userList",userList);
        request.setAttribute("organList",organList);
        UserOrgan userOrgan=userOrganService.selectById(id);
        request.setAttribute("userOrgan",userOrgan);
        return "gck/userOrgan/update";
    }

    /**
     * 修改用户关联组织
     *
     * @param userOrgan 用户关联组织
     * @return {@link ReturnBean}
     */
    @KrtLog("修改用户关联组织")
    @RequiresPermissions("UserOrgan:userOrgan:update")
    @PostMapping("gck/userOrgan/update")
    @ResponseBody
    public ReturnBean update(UserOrgan userOrgan) {
        userOrganService.updateById(userOrgan);
        return ReturnBean.ok();
    }

    /**
     * 删除用户关联组织
     *
     * @param id 用户关联组织id
     * @return {@link ReturnBean}
     */
    @KrtLog("删除用户关联组织")
    @RequiresPermissions("UserOrgan:userOrgan:delete")
    @PostMapping("gck/userOrgan/delete")
    @ResponseBody
    public ReturnBean delete(Integer id) {
        userOrganService.deleteById(id);
        return ReturnBean.ok();
    }

    /**
     * 批量删除用户关联组织
     *
     * @param ids 用户关联组织ids
     * @return {@link ReturnBean}
     */
    @KrtLog("批量删除用户关联组织")
    @RequiresPermissions("UserOrgan:userOrgan:delete")
    @PostMapping("gck/userOrgan/deleteByIds")
    @ResponseBody
    public ReturnBean deleteByIds(Integer[] ids) {
        userOrganService.deleteByIds(Arrays.asList(ids));
        return ReturnBean.ok();
    }


}
