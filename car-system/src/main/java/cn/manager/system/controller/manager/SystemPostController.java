package cn.manager.system.controller.manager;


import cn.manager.common.core.PageVo;
import cn.manager.common.core.ResultResponse;
import cn.manager.common.enums.ResponseEnum;
import cn.manager.common.exception.ServiceException;

import cn.manager.system.entity.SystemPost;
import cn.manager.system.entity.SystemUser;
import cn.manager.system.entity.SystemUserPost;
import cn.manager.system.service.SystemPostService;

import cn.manager.system.service.SystemUserPostService;
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

import java.util.List;

/**
 * <p>
 * 岗位信息表 前端控制器
 * </p>
 *
 * @author ljc
 * @since 2024-03-10
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/post")
public class SystemPostController {

    private final SystemPostService systemPostService;

    private final SystemUserPostService systemUserPostService;


    /**
     * 构造器建造
     */
    private LambdaQueryWrapper<SystemPost> queryPostWrapperBuild(SystemPost systemPost) {

        return Wrappers.<SystemPost>lambdaQuery()
                .eq(systemPost.getPostId() != null, SystemPost::getPostId, systemPost.getPostId())
                .eq(systemPost.getStatus() != null, SystemPost::getStatus, systemPost.getStatus())
                .eq(StringUtils.isNotBlank(systemPost.getPostName()), SystemPost::getPostName, systemPost.getPostName());
    }

    /**
     * 01-添加岗位
     */
    @PreAuthorize("hasPermission('system:post:add')")
    @PostMapping("/addPost")
    public ResultResponse addPost(@Validated @RequestBody SystemPost systemPost) {

        log.info("=> 添加岗位 <=");

        /*check: postName is unique*/
        checkPostByName(systemPost.getPostName(), 0);

        systemPostService.save(systemPost);

        return ResultResponse.ok("操作成功!");
    }

    /**
     * 02-查询岗位
     */
//    @PreAuthorize("hasPermission('system:post:list')")
    @GetMapping("/showPostList")
    public ResultResponse showPostList(SystemPost systemPost, PageVo pageVo) {

        log.info("=> 查询岗位 <=");

        IPage<SystemPost> iPage = new Page<>(pageVo.getPage(), pageVo.getSize());

        iPage = systemPostService.page(iPage, queryPostWrapperBuild(systemPost)
                .between(pageVo.checkBeginEndTime(), SystemPost::getCreateTime, pageVo.getBeginTime(), pageVo.getEndTime())
                .orderByDesc(SystemPost::getCreateTime));

        return ResultResponse.ok().setData(iPage);
    }

    /**
     * 03-修改岗位
     */
    @PreAuthorize("hasPermission('system:post:edit')")
    @PutMapping("/editPost")
    public ResultResponse editPost(@RequestBody SystemPost systemPost) {

        log.info("=> 修改岗位 <=");

        /*check: postName is unique, but exclusive oneself*/
        checkPostByName(systemPost.getPostName(), 1);

        return ResultResponse.booleanToResponse(systemPostService.updateById(systemPost));
    }

    /**
     * 04-删除岗位
     */
    @PreAuthorize("hasPermission('system:post:delete')")
    @DeleteMapping("/delPost/{id}")
    public ResultResponse delPost(@PathVariable("id") Long id) {

        log.info("=> 删除岗位 <=");

        /*校验: 是否分配*/
        if (systemUserPostService.count(Wrappers.<SystemUserPost>lambdaQuery().eq(SystemUserPost::getPostId, id)) > 0) {
            return ResultResponse.error("此岗位已经分配用户,不可删除!");
        }

        return ResultResponse.booleanToResponse(systemPostService.removeById(id));
    }

    /**
     * 根据Post名字查询数量
     *
     * @param postName    岗位名
     * @param checkCounts 检查数量(逻辑是大于)
     */
    private void checkPostByName(String postName, int checkCounts) {


        if (StringUtils.isBlank(postName)) return;

        int realCounts = systemPostService.count(queryPostWrapperBuild(new SystemPost().setPostName(postName)));

        /*check: 只要查出的数据比传入数据大 直接抛异常*/
        if (realCounts > checkCounts) {
            throw new ServiceException(ResponseEnum.A10001, "岗位名重复!");
        }

    }


}

