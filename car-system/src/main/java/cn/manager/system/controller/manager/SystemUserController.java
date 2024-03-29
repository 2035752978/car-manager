package cn.manager.system.controller.manager;


import cn.manager.common.core.PageVo;
import cn.manager.common.core.ResultResponse;
import cn.manager.system.entity.SystemRole;
import cn.manager.system.entity.SystemUser;
import cn.manager.system.entity.SystemUserPost;
import cn.manager.system.service.SystemUserPostService;
import cn.manager.system.service.SystemUserService;
import cn.manager.system.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author ljc
 * @since 2024-03-04
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/user")
public class SystemUserController {

    private final SystemUserService systemUserService;

    private final SystemUserPostService systemUserPostService;

    /**
     * 查询用户
     */
    @GetMapping("/showUserList")
    public ResultResponse showUserList(SystemUser systemUser, PageVo pageVo) {

        IPage<SystemUser> iPage = systemUserService.showUserList(systemUser, pageVo);

        return ResultResponse.ok().setData(iPage);
    }

    /**
     * 删除用户
     */
    @PreAuthorize("hasPermission('system:user:delete')")
    @DeleteMapping("/delUser/{id}")
    public ResultResponse delUser(@PathVariable("id") Long id) {

        if (SecurityUtils.isAdmin(id)) {
            return ResultResponse.error("超级管理员不可操作");
        }

        systemUserService.removeById(id);

        //清除对应关系
        systemUserPostService.remove(Wrappers.lambdaQuery(new SystemUserPost(id, null)));

        return ResultResponse.ok();
    }

    /**
     * 添加用户
     */
    @PreAuthorize("hasPermission('system:user:add')")
    @PostMapping("/addUser")
    public ResultResponse addUser(@Validated @RequestBody SystemUser systemUser) {

        //密码加密
        systemUser.setPassword(SecurityUtils.encodePassword(systemUser.getPassword()));

        systemUserService.addUser(systemUser);

        return ResultResponse.ok("操作成功!");
    }

    /**
     * 修改用户
     */
    @PreAuthorize("hasPermission('system:user:edit')")
    @PutMapping("/editUser")
    public ResultResponse editUser(@RequestBody SystemUser systemUser) {

        systemUserService.updateUser(systemUser);

        return ResultResponse.ok("操作成功!");
    }


//    @PostMapping("/isRegistered")
//    public ResultResponse isRegistered(String phone) {
//
//        SystemUser user = systemUserService.getOne(new QueryWrapper<SystemUser>().eq("phone", phone));
//
//        if (user == null) {
//            return ResultResponse.error("抱歉, 未查到该用户信息!!!");
//        }
//
//        return ResultResponse.ok("查询到用户信息!!!").setData(user);
//    }
}

