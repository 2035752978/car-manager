package cn.manager.system.controller.manager;


import cn.manager.common.core.Lists;
import cn.manager.common.core.PageVo;
import cn.manager.common.core.ResultResponse;
import cn.manager.common.dto.request.RoleAllotReqDTO;
import cn.manager.common.enums.ResponseEnum;
import cn.manager.common.exception.ServiceException;
import cn.manager.system.entity.*;
import cn.manager.system.service.SystemMenuService;
import cn.manager.system.service.SystemRoleMenuService;
import cn.manager.system.service.SystemRoleService;
import cn.manager.system.service.SystemUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色信息表
 * </p>
 *
 * @author ljc
 * @since 2024-03-08
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/role")
public class SystemRoleController {

    private final SystemRoleService systemRoleService;

    private final SystemRoleMenuService systemRoleMenuService;

    private final SystemMenuService systemMenuService;

    private final SystemUserService systemUserService;

    /**
     * 构造器建造
     */
    private LambdaQueryWrapper<SystemRole> queryRoleWrapperBuild(SystemRole systemRole) {

        return Wrappers.<SystemRole>lambdaQuery()
                .eq(systemRole.getRoleId() != null, SystemRole::getRoleId, systemRole.getRoleId())
                .eq(systemRole.getStatus() != null, SystemRole::getStatus, systemRole.getStatus())
                .eq(StringUtils.isNotBlank(systemRole.getRoleName()), SystemRole::getRoleName, systemRole.getRoleName());
    }

    /**
     * 查询角色
     */
    @GetMapping("/showRoleList")
    public ResultResponse showRoleList(SystemRole systemRole, PageVo pageVo) {

        log.info("=> 查询角色 <=");

        IPage<SystemRole> iPage = new Page<>(pageVo.getPage(), pageVo.getSize());

        iPage = systemRoleService.page(iPage, queryRoleWrapperBuild(systemRole)
                .orderByDesc(SystemRole::getRoleSort));

        return ResultResponse.ok().setData(iPage);
    }

    /**
     * 删除角色
     */
    @PreAuthorize("hasPermission('system:role:delete')")
    @DeleteMapping("/delRole/{id}")
    public ResultResponse delRole(@PathVariable("id") Long id) {

        log.info("=> 删除角色 <=");

        /*校验: 是否分配*/
        if (systemUserService.count(Wrappers.<SystemUser>lambdaQuery().eq(SystemUser::getRoleId, id)) > 0) {
            return ResultResponse.error("此角色已经分配用户,不可删除!");
        }


        return ResultResponse.booleanToResponse(systemRoleService.removeById(id));
    }

    /**
     * 添加角色
     */
    @PreAuthorize("hasPermission('system:role:add')")
    @PostMapping("/addRole")
    public ResultResponse addRole(@Validated @RequestBody SystemRole systemRole) {

        log.info("=> 添加角色 <=");

        /*check: roleName is unique*/
        checkRoleByName(systemRole.getRoleName(), 0);

        systemRoleService.save(systemRole);

        return ResultResponse.ok("操作成功!");
    }

    /**
     * 修改角色
     */
    @PreAuthorize("hasPermission('system:role:edit')")
    @PutMapping("/editRole")
    public ResultResponse editRole(@RequestBody SystemRole systemRole) {

        log.info("=> 修改角色 <=");

        /*check: roleName is unique, but exclusive oneself*/
        checkRoleByName(systemRole.getRoleName(), 1);

        return ResultResponse.booleanToResponse(systemRoleService.updateById(systemRole));
    }


    /**
     * 05-给角色分配权限
     */
    @PreAuthorize("hasPermission('system:role:allot')")
    @Transactional
    @PostMapping("/allotRoleMenu")
    public ResultResponse allotRoleMenu(@RequestBody RoleAllotReqDTO roleAllotReqDTO) {

        log.info("=> 给角色分配权限 <=");

        Long roleId = roleAllotReqDTO.getRoleId();
        List<Long> menuIds = roleAllotReqDTO.getMenuIds();

        //测试时临时使用
//        menuIds = systemMenuService.list(Wrappers.<SystemMenu>lambdaQuery().select(SystemMenu::getMenuId)).stream().map(SystemMenu::getMenuId).distinct().collect(Collectors.toList());


        /*校验传入参数*/
        if (CollectionUtils.isEmpty(menuIds) || roleId == null) {
            return ResultResponse.error("请求参数有误!");
        }

        if (Objects.equals(roleId, 1L)) {
            return ResultResponse.error("超级管理员默认全部权限,无需分配");
        }

        /*角色列表*/
        List<SystemRoleMenu> roleMenuList = Lists.newArrayList();
        menuIds.forEach(menuId -> roleMenuList.add(new SystemRoleMenu(roleId, menuId)));

        //删除关系表
        systemRoleMenuService.remove(Wrappers.<SystemRoleMenu>lambdaQuery()
                .eq(SystemRoleMenu::getRoleId, roleId));

        //批量添加
        systemRoleMenuService.saveBatch(roleMenuList);

        return ResultResponse.ok("操作成功!");
    }


    /**
     * 根据role名字查询数量
     *
     * @param roleName    角色名
     * @param checkCounts 检查数量(逻辑是大于)
     */
    private void checkRoleByName(String roleName, int checkCounts) {


        if (StringUtils.isBlank(roleName)) return;

        int realCounts = systemRoleService.count(queryRoleWrapperBuild(new SystemRole().setRoleName(roleName)));

        /*check: 只要查出的数据比传入数据大 直接抛异常*/
        if (realCounts > checkCounts) {
            throw new ServiceException(ResponseEnum.A10001, "角色名重复!");
        }

    }


}

