package cn.manager.system.controller.manager;


import cn.manager.common.core.PageVo;
import cn.manager.common.core.ResultResponse;
import cn.manager.system.entity.SystemNotice;
import cn.manager.system.service.SystemNoticeService;
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

/**
 * <p>
 * 通知公告表  
 * </p>
 *
 * @author ljc
 * @since 2024-03-09
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/notice")
public class SystemNoticeController {

    private final SystemNoticeService systemNoticeService;

    /**
     * 构造器建造
     */
    private LambdaQueryWrapper<SystemNotice> queryNoticeWrapperBuild(SystemNotice systemNotice) {

        return Wrappers.<SystemNotice>lambdaQuery()
                .eq(systemNotice.getNoticeId() != null, SystemNotice::getNoticeId, systemNotice.getNoticeId())
                .eq(systemNotice.getStatus() != null, SystemNotice::getStatus, systemNotice.getStatus())
                .eq(StringUtils.isNotBlank(systemNotice.getNoticeTitle()), SystemNotice::getNoticeTitle, systemNotice.getNoticeTitle())
               ;
    }

    /**
     * 查询公告
     */
    @GetMapping("/showNoticeList")
    public ResultResponse showNoticeList(SystemNotice systemNotice, PageVo pageVo) {

        log.info("=> 查询公告 <=");

        IPage<SystemNotice> iPage = new Page<>(pageVo.getPage(), pageVo.getSize());

        iPage = systemNoticeService.page(iPage, queryNoticeWrapperBuild(systemNotice)
                .between(pageVo.checkBeginEndTime(), SystemNotice::getCreateTime, pageVo.getBeginTime(), pageVo.getEndTime())
                .orderByDesc(SystemNotice::getCreateTime));

        return ResultResponse.ok().setData(iPage);
    }

    /**
     * 删除公告
     */
    @PreAuthorize("hasPermission('system:notice:delete')")
    @DeleteMapping("/delNotice/{id}")
    public ResultResponse delNotice(@PathVariable("id") Long id) {

        log.info("=> 删除公告 <=");

        return ResultResponse.booleanToResponse(systemNoticeService.removeById(id));
    }

    /**
     * 添加公告
     */
    @PreAuthorize("hasPermission('system:notice:add')")
    @PostMapping("/addNotice")
    public ResultResponse addNotice(@Validated @RequestBody SystemNotice systemNotice) {

        log.info("=> 添加公告 <=");

        return ResultResponse.booleanToResponse(systemNoticeService.save(systemNotice));

    }

    /**
     * 修改公告
     */
    @PreAuthorize("hasPermission('system:notice:edit')")
    @PutMapping("/editNotice")
    public ResultResponse editNotice(@RequestBody SystemNotice systemNotice) {

        log.info("=> 修改公告 <=");

        return ResultResponse.booleanToResponse(systemNoticeService.updateById(systemNotice));
    }


}

