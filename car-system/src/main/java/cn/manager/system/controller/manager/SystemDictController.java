package cn.manager.system.controller.manager;


import cn.manager.common.core.PageVo;
import cn.manager.common.core.ResultResponse;
import cn.manager.system.entity.SystemDict;
import cn.manager.system.entity.SystemRole;
import cn.manager.system.service.SystemDictService;
import cn.manager.system.service.SystemRoleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 字典数据表 前端控制器
 * </p>
 *
 * @author ljc
 * @since 2024-03-19
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/dict")
public class SystemDictController {

    private final SystemDictService systemDictService;
    /**
     * 查询字典信息
     */
    @GetMapping("/showRoleList")
    public ResultResponse showRoleList(SystemDict systemDict) {

        log.info("=> 查询字典信息 <=");

        List<SystemDict> dictList = systemDictService.list(Wrappers.<SystemDict>lambdaQuery()
                .eq(SystemDict::getDictLevel, systemDict.getDictLevel()));

        return ResultResponse.ok().setData(dictList);
    }

}

