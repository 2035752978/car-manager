package cn.manager.system.service.impl;

import cn.manager.common.core.Lists;
import cn.manager.common.core.PageVo;
import cn.manager.common.core.ResultResponse;
import cn.manager.system.entity.SystemMenu;

import cn.manager.system.entity.SystemRoleMenu;
import cn.manager.system.mapper.SystemMenuMapper;
import cn.manager.system.mapper.SystemRoleMenuMapper;
import cn.manager.system.service.SystemMenuService;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author ljc
 * @since 2024-03-09
 */
@RequiredArgsConstructor
@Service
public class SystemMenuServiceImpl extends ServiceImpl<SystemMenuMapper, SystemMenu> implements SystemMenuService {

    private final SystemRoleMenuMapper systemRoleMenuMapper;


    @Override
    public ResultResponse showMenuList(boolean mCFlag, PageVo pageVo) {
        Long roleId = pageVo.getKeyId();
        List<Long> menuIds = Lists.newArrayList();
        //给修改权限基础树
        List<SystemMenu> menuList = baseMapper.selectList(Wrappers.<SystemMenu>lambdaQuery()
                .in(mCFlag, SystemMenu::getMenuType, "M", "C")
                .eq(SystemMenu::getStatus, 0)
                .orderByAsc(SystemMenu::getOrderNum)
        );

        //角色id不为空并且不能是超级管理员 查询权限
        if (roleId != null && !Objects.equals(roleId, 1L)) {
            List<SystemRoleMenu> roleMenuList = systemRoleMenuMapper.selectList(
                    Wrappers.<SystemRoleMenu>lambdaQuery().eq(SystemRoleMenu::getRoleId, roleId)

            );
            if (CollectionUtils.isNotEmpty(roleMenuList)) {
                menuIds = roleMenuList.stream().map(SystemRoleMenu::getMenuId).distinct().collect(Collectors.toList());
            }
            List<Long> finalMenuIds = menuIds;

            if (CollectionUtils.isNotEmpty(finalMenuIds)) {

                //可以优化(不用数据库操作) TODO
                List<SystemMenu> menuList1 = baseMapper.selectList(Wrappers.<SystemMenu>lambdaQuery()
//                        .in(mCFlag, SystemMenu::getMenuType, "M", "C")
                        .in(SystemMenu::getId, finalMenuIds));
                List<Long> parentIds = menuList1.stream().map(SystemMenu::getParentId).distinct().collect(Collectors.toList());

                finalMenuIds.addAll(parentIds);
                //查询新的菜单
                menuList1 = baseMapper.selectList(Wrappers.<SystemMenu>lambdaQuery()
                                .in(SystemMenu::getId, finalMenuIds)
                                .orderByAsc(SystemMenu::getOrderNum)
//                        .in(mCFlag, SystemMenu::getMenuType, "M", "C")
                );
                if (mCFlag) {
                    //登录: 处理menuList1 只要MC
                    menuList1 = menuList1.stream().filter(menuDto -> !Objects.equals(menuDto.getMenuType(), "F")).collect(Collectors.toList());

                    return ResultResponse.ok().setData(buildTree(menuList1));
                } else {
                    //用基础树加key对应
                    return ResultResponse.ok().setData(buildTree(menuList)).put("checkKeys", menuIds);
                }

            } else {//超级管理员

                if (mCFlag) {
                    return ResultResponse.ok("没权限").setData(Lists.newArrayList());
                } else {
                    return ResultResponse.ok().setData(buildTree(menuList)).put("checkKeys", Lists.newArrayList());
                }

            }
        }

        return ResultResponse.ok().setData(buildTree(menuList));
    }

    /**
     * 使用递归方法建菜单
     */
    public static List<SystemMenu> buildTree(List<SystemMenu> sysMenuList) {

        List<SystemMenu> trees = new ArrayList<>();

        for (SystemMenu sysMenu : sysMenuList) {
            //父级为0(建造一级)
            if (Objects.equals(sysMenu.getParentId(), 0L)) {
                trees.add(findChildren(sysMenu, sysMenuList));
            }
        }

        return trees;
    }

    /**
     * 递归查找子节点(建造2-n级)
     */
    private static SystemMenu findChildren(SystemMenu sysMenu, List<SystemMenu> treeNodes) {
        if (sysMenu.getChildren() == null) {
            sysMenu.setChildren(new ArrayList<>());
        }

        for (SystemMenu thisMenu : treeNodes) {
            /*即当前菜单的id值与所有菜单的parent_id值相等*/
            if (Objects.equals(sysMenu.getId(), thisMenu.getParentId())) {
                sysMenu.getChildren().add(findChildren(thisMenu, treeNodes));
            }
        }

        return sysMenu;
    }
//废弃
//    /**
//     * 使用递归方法建菜单(建造一级) 避免重复查找(缺点 无顺序)
//     *
//     * @param sysMenuList 集合
//     * @return 树
//     */
//    public static List<SystemMenu> buildTree(List<SystemMenu> sysMenuList) {
//        List<SystemMenu> trees = new ArrayList<>();
//        Map<Long, SystemMenu> menuMap = sysMenuList.stream().collect(Collectors.toMap(SystemMenu::getId, Function.identity()));
//
//        // 初始化菜单映射
//        for (SystemMenu sysMenu : sysMenuList) {
//            menuMap.put(sysMenu.getId(), sysMenu);
//        }
//
//        for (SystemMenu sysMenu : sysMenuList) {
//            // 父级为0（建造一级）
//            if (Objects.equals(sysMenu.getParentId(), 0L)) {
//                findChildren(sysMenu, menuMap);
//                trees.add(sysMenu);
//            }
//        }
//
//        return trees;
//    }
//
//    /**
//     * 递归查找子节点(建造2-n级)
//     *
//     * @param menuMap parent=0后面的全部数据(查出子集等等)
//     * @return SystemMenu
//     */
//    private static SystemMenu findChildren(SystemMenu sysMenu, Map<Long, SystemMenu> menuMap) {
//        if (sysMenu.getChildren() == null) {
//            sysMenu.setChildren(new ArrayList<>());
//        }
//
//        for (SystemMenu item : menuMap.values()) {
//            /*即当前菜单的id值与所有菜单的parent_id值相等*/
//            if (Objects.equals(sysMenu.getId(), item.getParentId())) {
//                SystemMenu child = findChildren(item, menuMap);
//                sysMenu.getChildren().add(child);
//            }
//        }
//
//        return sysMenu;
//    }
}
