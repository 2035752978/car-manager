package cn.manager.system.controller.car;


import cn.manager.common.core.PageVo;
import cn.manager.common.core.ResultResponse;
import cn.manager.common.dto.request.CarStallRecordReqDto;
import cn.manager.common.dto.response.StallCompareRespDTO;
import cn.manager.common.utils.StringUtils;
import cn.manager.system.entity.CarStallRecord;
import cn.manager.system.service.CarStallRecordService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 车辆进出口记录表
 * </p>
 *
 * @author ljc
 * @since 2024-03-16
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/car/stallRecord")
public class CarStallRecordController {

    private final CarStallRecordService carStallRecordService;

    /**
     * 01-查询车辆进出口记录
     */
//    @PreAuthorize("hasPermission('car:stallRecord:list')")
    @GetMapping("/showStallRecordList")
    public ResultResponse showStallRecordList(PageVo pageVo) {

        log.info("=> 查询车辆进出口记录 <=");

        return carStallRecordService.showStallRecordList(pageVo);
    }

    /**
     * 02(1)- 查询进出口类型(先获取类型 再走02接口)
     */
    @GetMapping("/getAccessInOrOutType")
    public ResultResponse getAccessInOrOutType(CarStallRecordReqDto carStallRecordReqDto) {

        log.info("===> 查询进出口类型 <===");

        if (carStallRecordReqDto == null || StringUtils.isBlank(carStallRecordReqDto.getPlateNumber())) {
            return ResultResponse.error("车牌信息不可为空!");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("accessType", carStallRecordService.getAccessInOrOutType(carStallRecordReqDto.getPlateNumber()));
        map.put("plateNumber", carStallRecordReqDto.getPlateNumber());

        return ResultResponse.ok("查询成功").setData(map);
    }


    /**
     * 02-添加车辆进出口记录
     */
    @PreAuthorize("hasPermission('car:stallRecord:add')")
    @PostMapping("/addStallRecord")
    public ResultResponse addStallRecord(@Validated @RequestBody CarStallRecordReqDto carStallRecordReqDto) {

        log.info("=> 添加车辆进出口记录 <=");

        carStallRecordService.addStallRecord(carStallRecordReqDto);

        return ResultResponse.booleanToResponse(true);

    }

    /**
     * 01-车辆文明评比
     */
//    @PreAuthorize("hasPermission('analyse:stallRecord:compare')")
    @GetMapping("/stallAnalyseCompare")
    public ResultResponse stallAnalyseCompare(StallCompareRespDTO stallCompareRespDTO, PageVo pageVo) {

        log.info("=> 车辆文明评比 <=");

        return carStallRecordService.stallAnalyseCompare(stallCompareRespDTO, pageVo);
    }

}

