package cn.manager.system.controller.manager;

import cn.manager.common.core.ResultResponse;
import cn.manager.system.entity.SystemUnifySet;
import cn.manager.system.service.SystemUnifySetService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 系统统一设置 前端控制器
 * </p>
 * 添加/删除均不允许 手动添加
 *
 * @author ljc
 * @since 2024-03-16
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/unifySet")
public class SystemUnifySetController {

    private final SystemUnifySetService systemUnifySetService;


    /**
     * 01-查询车位管理(系统设置)
     */
//    @PreAuthorize("hasPermission('system:unifySet:list')")
    @GetMapping("/showUnifySetList")
    public ResultResponse showUnifySetList(SystemUnifySet systemUnifySet) {

        log.info("=> 查询车位管理(系统设置) <=");

        List<SystemUnifySet> unifyCacheList = systemUnifySetService.showUnifySetList(systemUnifySet);


        return ResultResponse.ok().setData(unifyCacheList);
    }

    /**
     * 02-修改车位管理(系统设置)
     */
    @PreAuthorize("hasPermission('system:unifySet:edit')")
    @PutMapping("/editUnifySet")
    public ResultResponse editUnifySet(@RequestBody SystemUnifySet systemUnifySet) {

        log.info("=> 修改车位管理(系统设置) <=");

        if (systemUnifySet.getId() == null) {
            return ResultResponse.error("请选择操作的信息!");
        }

        systemUnifySetService.editUnifySet(systemUnifySet);

        return ResultResponse.booleanToResponse(true);
    }


    /**
     * 03-刷新车位管理(系统设置)缓存
     */
    @PreAuthorize("hasPermission('system:unifySet:refresh')")
    @PutMapping("/refreshUnifySet")
    public ResultResponse refreshUnifySet() {

        log.info("=> 03-刷新车位管理(系统设置)缓存 <=");

        systemUnifySetService.refreshUnifySet();

        return ResultResponse.booleanToResponse(true);
    }
}

