package cn.manager.system.controller.car;


import cn.manager.common.core.PageVo;
import cn.manager.common.core.ResultResponse;
import cn.manager.common.utils.IdGenerateUtils;
import cn.manager.system.entity.CarStallAppoint;
import cn.manager.system.service.CarStallAppointService;
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
 * 车辆(位)预约记录表 前端控制器
 * </p>
 *
 * @author ljc
 * @since 2024-03-16
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/car/appoint")
public class CarStallAppointController {

    private final CarStallAppointService carStallAppointService;

    /**
     * 01-查询车辆预约记录
     */
    //    @PreAuthorize("hasPermission('car:appoint:list')")
    @GetMapping("/showAppointList")
    public ResultResponse showAppointList(CarStallAppoint carStallAppoint, PageVo pageVo) {

        log.info("=> 查询车辆预约记录 <=");

        IPage<CarStallAppoint> iPage = new Page<>(pageVo.getPage(), pageVo.getSize());

        iPage = carStallAppointService.page(iPage, queryAppointWrapperBuild(carStallAppoint)

                //查询预约开始时间范围
                .between(pageVo.checkBeginEndTime(), CarStallAppoint::getApBeginTime, pageVo.getBeginTime(), pageVo.getEndTime())

                .orderByDesc(CarStallAppoint::getCreateTime));

        return ResultResponse.ok().setData(iPage);
    }


    /**
     * 02-添加车辆预约记录
     */
    @PreAuthorize("hasPermission('car:appoint:add')")
    @PostMapping("/addAppoint")
    public ResultResponse addAppoint(@Validated @RequestBody CarStallAppoint carStallAppoint) {

        log.info("=> 添加车辆预约记录 <=");

        carStallAppointService.addAppoint(carStallAppoint);

        return ResultResponse.booleanToResponse(true);

    }

    /**
     * 03-修改车辆预约记录
     */
    @PreAuthorize("hasPermission('car:appoint:edit')")
    @PutMapping("/editAppoint")
    public ResultResponse editAppoint(@RequestBody CarStallAppoint carStallAppoint) {

        log.info("=> 修改车辆预约记录 <=");

        return ResultResponse.booleanToResponse(carStallAppointService.updateById(carStallAppoint));
    }

    /**
     * 04-删除车辆预约记录
     */
    @PreAuthorize("hasPermission('car:appoint:delete')")
    @DeleteMapping("/delAppoint/{id}")
    public ResultResponse delAppoint(@PathVariable("id") Long id) {

        log.info("=> 删除车辆预约记录 <=");

        return ResultResponse.booleanToResponse(carStallAppointService.removeById(id));
    }

    /**
     * 构造器建造
     */
    private LambdaQueryWrapper<CarStallAppoint> queryAppointWrapperBuild(CarStallAppoint carStallAppoint) {

        return Wrappers.<CarStallAppoint>lambdaQuery()
                .eq(carStallAppoint.getId() != null, CarStallAppoint::getId, carStallAppoint.getId())
                .eq(carStallAppoint.getApStatus() != null, CarStallAppoint::getApStatus, carStallAppoint.getApStatus())
                .eq(StringUtils.isNotBlank(carStallAppoint.getPlateNumber()), CarStallAppoint::getPlateNumber, carStallAppoint.getPlateNumber());

    }

}

