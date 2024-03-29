package cn.manager.system.controller.manager;


import cn.manager.common.core.PageVo;
import cn.manager.common.core.ResultResponse;
import cn.manager.system.entity.SystemMenu;
import cn.manager.system.entity.SystemRoleMenu;
import cn.manager.system.service.SystemMenuService;
import cn.manager.system.service.SystemRoleMenuService;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * <p>
 * 菜单权限表
 * </p>
 *
 * @author ljc
 * @since 2024-03-09
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/menu")
public class SystemMenuController {

    private final SystemMenuService systemMenuService;

    private final SystemRoleMenuService systemRoleMenuService;


    /**
     * 01-添加菜单
     */
    @PreAuthorize("hasPermission('system:menu:add')")
    @PostMapping("/addMenu")
    public ResultResponse addMenu(@Validated @RequestBody SystemMenu systemMenu) {

        log.info("=> 添加菜单 <=");

        if (systemMenu.getParentId() == null) {
            return ResultResponse.error("父级id不能为空!");
        }

        systemMenuService.save(systemMenu);

        return ResultResponse.ok("操作成功!");
    }


    /**
     * 02-查询菜单(树结构)
     */
    @GetMapping("/showMenuList")
    public ResultResponse showMenuList(PageVo pageVo) {

        log.info("=> 查询菜单 <=");

        return systemMenuService.showMenuList(false, pageVo);
    }


    /**
     * 03-修改菜单
     */
    @PreAuthorize("hasPermission('system:menu:edit')")
    @PutMapping("/editMenu")
    public ResultResponse editMenu(@RequestBody SystemMenu systemMenu) {

        log.info("=> 修改菜单 <=");

        if (Objects.equals(systemMenu.getId(), systemMenu.getParentId())) {
            return ResultResponse.error("不能将自己选为上级菜单");
        }

        return ResultResponse.booleanToResponse(systemMenuService.updateById(systemMenu));
    }

    /**
     * 04-删除菜单
     */
    @PreAuthorize("hasPermission('system:menu:delete')")
    @DeleteMapping("/delMenu/{id}")
    public ResultResponse delMenu(@PathVariable("id") Long id) {

        log.info("=> 删除菜单 <=");

        /*校验: 是否分配*/
        if (systemRoleMenuService.count(Wrappers.<SystemRoleMenu>lambdaQuery().eq(SystemRoleMenu::getMenuId, id)) > 0) {
            return ResultResponse.error("此菜单已经分配角色,不可删除!");
        }

        return ResultResponse.booleanToResponse(systemMenuService.removeById(id));
    }


}

