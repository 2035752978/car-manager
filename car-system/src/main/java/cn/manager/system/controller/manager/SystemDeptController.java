package cn.manager.system.controller.manager;


import cn.manager.common.core.PageVo;
import cn.manager.common.core.ResultResponse;
import cn.manager.common.enums.ResponseEnum;
import cn.manager.common.exception.ServiceException;
import cn.manager.system.entity.SystemDept;
import cn.manager.system.entity.SystemMenu;
import cn.manager.system.entity.SystemUser;
import cn.manager.system.service.SystemDeptService;
import cn.manager.system.service.SystemUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 部门表 前端控制器
 * </p>
 *
 * @author ljc
 * @since 2024-03-10
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/dept")
public class SystemDeptController {

    private final SystemDeptService systemDeptService;

    private final SystemUserService systemUserService;

    /**
     * 构造器建造
     */
    private LambdaQueryWrapper<SystemDept> queryDeptWrapperBuild(SystemDept systemDept) {

        return Wrappers.<SystemDept>lambdaQuery()
                .eq(systemDept.getDeptId() != null, SystemDept::getDeptId, systemDept.getDeptId())
                .eq(systemDept.getStatus() != null, SystemDept::getStatus, systemDept.getStatus())
                .eq(StringUtils.isNotBlank(systemDept.getDeptName()), SystemDept::getDeptName, systemDept.getDeptName());
    }

    /**
     * 01-添加部门
     */
    @PreAuthorize("hasPermission('system:dept:add')")
    @PostMapping("/addDept")
    public ResultResponse addDept(@Validated @RequestBody SystemDept systemDept) {

        log.info("=> 添加部门 <=");

        /*check: deptName is unique*/
        checkDeptByName(systemDept.getDeptName(), 0);

        //获取原型等级
        getAncestorsLevel(systemDept);

        systemDeptService.save(systemDept);

        return ResultResponse.ok("操作成功!");
    }

    /**
     * 02-查询部门
     */
//    @PreAuthorize("hasPermission('system:dept:list')")
    @GetMapping("/showDeptList")
    public ResultResponse showDeptList(SystemDept systemDept, PageVo pageVo) {

        log.info("=> 查询部门 <=");

        IPage<SystemDept> iPage = new Page<>(pageVo.getPage(), pageVo.getSize());

        iPage = systemDeptService.page(iPage, queryDeptWrapperBuild(systemDept)
                .between(pageVo.checkBeginEndTime(), SystemDept::getCreateTime, pageVo.getBeginTime(), pageVo.getEndTime())
                .orderByDesc(SystemDept::getCreateTime));

        return ResultResponse.ok().setData(iPage);
    }

    /**
     * 021-查询部门(树)
     */
    @GetMapping("/showDeptTree")
    public ResultResponse showDeptTree(SystemDept systemDept, PageVo pageVo) {

        log.info("=> 查询部门(树) <=");

        List<SystemDept> list = systemDeptService.list(queryDeptWrapperBuild(systemDept));

        return ResultResponse.ok().setData(buildTree(list));
    }



    /**
     * 03-修改部门
     */
    @PreAuthorize("hasPermission('system:dept:edit')")
    @PutMapping("/editDept")
    public ResultResponse editDept(@RequestBody SystemDept systemDept) {

        log.info("=> 修改部门 <=");

        /*check: deptName is unique, but exclusive oneself*/
        checkDeptByName(systemDept.getDeptName(), 1);

        //获取原型等级
        getAncestorsLevel(systemDept);

        return ResultResponse.booleanToResponse(systemDeptService.updateById(systemDept));
    }

    /**
     * 04-删除部门
     */
    @PreAuthorize("hasPermission('system:dept:delete')")
    @DeleteMapping("/delDept/{id}")
    public ResultResponse delDept(@PathVariable("id") Long id) {

        log.info("=> 删除部门 <=");

        /*校验: 是否分配*/
        if (systemUserService.count(Wrappers.<SystemUser>lambdaQuery().eq(SystemUser::getDeptId, id)) > 0) {
            return ResultResponse.error("此部门已经分配用户,不可删除!");
        }

        return ResultResponse.booleanToResponse(systemDeptService.removeById(id));
    }

    /**
     * 根据Dept名字查询数量
     *
     * @param deptName    部门名
     * @param checkCounts 检查数量(逻辑是大于)
     */
    private void checkDeptByName(String deptName, int checkCounts) {

        if (StringUtils.isBlank(deptName)) return;

        int realCounts = systemDeptService.count(queryDeptWrapperBuild(new SystemDept().setDeptName(deptName)));

        /*check: 只要查出的数据比传入数据大 直接抛异常*/
        if (realCounts > checkCounts) {
            throw new ServiceException(ResponseEnum.A10001, "部门名重复!");
        }
    }


    /**
     * 获取原型等级
     *
     * @param systemDept
     */
    private void getAncestorsLevel(SystemDept systemDept) {
        /*部门等级设置*/
        if (Objects.equals(systemDept.getParentId(), 0L)) {
            systemDept.setAncestors("0");
            systemDept.setLevel(1);
        } else {
            SystemDept parentDept = systemDeptService.getById(systemDept.getParentId());
            if (parentDept == null) {
                throw new ServiceException(ResponseEnum.A10001, "选择的父级部门不存在, 请刷新后重试!");
            }
            systemDept.setAncestors(parentDept.getAncestors() + "," + systemDept.getParentId());
            systemDept.setLevel(parentDept.getLevel() + 1);
        }


    }

    /**
     * 使用递归方法建(建造一级)
     *
     * @return 树
     */
    public static List<SystemDept> buildTree(List<SystemDept> sysDeptList) {

        List<SystemDept> trees = new ArrayList<>();

        for (SystemDept sysMenu : sysDeptList) {
            //父级为0(建造一级)
            if (Objects.equals(sysMenu.getParentId(), 0L)) {
                trees.add(findChildren(sysMenu, sysDeptList));
            }
        }

        return trees;
    }

    /**
     * 递归查找子节点(建造2-n级)
     *
     * @param treeNodes parent=0后面的全部数据(查出子集等等)
     * @return systemDept
     */
    private static SystemDept findChildren(SystemDept systemDept, List<SystemDept> treeNodes) {

        for (SystemDept systemDept1 : treeNodes) {
            /*即当前菜单的id值与所有菜单的parent_id值相等*/
            if (Objects.equals(systemDept.getDeptId(), systemDept1.getParentId())) {
                systemDept.getChildren().add(findChildren(systemDept1, treeNodes));
            }
        }

        return systemDept;
    }


}

