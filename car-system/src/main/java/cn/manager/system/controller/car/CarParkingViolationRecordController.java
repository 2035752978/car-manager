package cn.manager.system.controller.car;

import java.util.Date;

import cn.manager.common.core.PageVo;
import cn.manager.common.core.ResultResponse;

import cn.manager.common.utils.IdGenerateUtils;
import cn.manager.system.entity.CarParkingViolationRecord;

import cn.manager.system.entity.CarStallAppoint;
import cn.manager.system.entity.CarUserRecord;
import cn.manager.system.entity.SystemUser;
import cn.manager.system.mapper.SystemUserMapper;
import cn.manager.system.service.CarParkingViolationRecordService;

import cn.manager.system.service.CarStallAppointService;
import cn.manager.system.service.CarUserRecordService;
import cn.manager.system.service.SystemUserService;
import cn.manager.system.utils.SendMailUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 车辆违停罚款记录表 前端控制器
 * </p>
 *
 * @author ljc
 * @since 2024-03-16
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/car/carViolation")
public class CarParkingViolationRecordController {

    private final CarParkingViolationRecordService carParkingViolationRecordService;

    private final CarUserRecordService carUserRecordService;

//    private final SystemUserService systemUserService;

    private final CarStallAppointService carStallAppointService;

    @Autowired
    private SystemUserMapper systemUserMapper;
    /**
     * 02-添加车辆违停罚款记录
     */
    @PreAuthorize("hasPermission('car:violation:add')")
    @PostMapping("/addViolation")
    public ResultResponse addViolation(@Validated @RequestBody CarParkingViolationRecord carParkingViolationRecord) {

        log.info("=> 添加车辆违停罚款记录 <=");

        String plateNumber = carParkingViolationRecord.getPlateNumber();

        CarUserRecord carUserRecord = carUserRecordService.getOne(
                Wrappers.<CarUserRecord>lambdaQuery(
                        new CarUserRecord().setPlateNumber(plateNumber))
        );
        if (carUserRecord == null) {
            CarStallAppoint appoint = carStallAppointService.getOne(Wrappers.<CarStallAppoint>lambdaQuery()
                    .eq(CarStallAppoint::getPlateNumber, plateNumber)
                    .orderByDesc(CarStallAppoint::getUpdateTime)
                    .eq(CarStallAppoint::getApStatus, 1)
                    .last("limit 1"));

            if (appoint == null) {
                return ResultResponse.error("此车辆不在校园内, 不需要记录呦~");
            }

            carParkingViolationRecord.setAppointId(appoint.getId());

        } else {
            carParkingViolationRecord.setUserId(carUserRecord.getUserId());
        }

        carParkingViolationRecord.setId(IdGenerateUtils.getInstance().nextId());
        carParkingViolationRecordService.save(carParkingViolationRecord);
        if (carUserRecord != null) {
            SystemUser user = systemUserMapper.getUserById(carUserRecord.getUserId());
            if (user != null && StringUtils.isNotBlank(user.getEmail())) {
                // 发送邮件
                // sendMailUtil.sendMail(user.getEmail(), "校园车辆违规提醒", "尊敬的用户您好，您的车辆存在违规行为，请及时处理，谢谢！");
                SendMailUtil sendMailUtil = new SendMailUtil();
                try {
                    sendMailUtil.sendMail(user.getEmail(),
                            carParkingViolationRecord.getPlateNumber(),
                            carParkingViolationRecord.getNoParkingArea(),
                            carParkingViolationRecord.getPenaltyMoney() );
                } catch (Exception e) {
                    log.error("发送邮件失败", e);
                }
            }
        }

        return ResultResponse.booleanToResponse(true);
    }

    /**
     * 03-修改车辆违停罚款记录
     */
    @PreAuthorize("hasPermission('car:violation:edit')")
    @PutMapping("/editViolation")
    public ResultResponse editViolation(@RequestBody CarParkingViolationRecord carParkingViolationRecord) {

        log.info("=> 修改车辆违停罚款记录 <=");

        return ResultResponse.booleanToResponse(carParkingViolationRecordService.updateById(carParkingViolationRecord));
    }

    /**
     * 04-删除车辆违停罚款记录
     */
    @PreAuthorize("hasPermission('car:violation:delete')")
    @DeleteMapping("/delViolation/{id}")
    public ResultResponse delViolation(@PathVariable("id") Long id) {

        log.info("=> 删除车辆违停罚款记录 <=");

        return ResultResponse.booleanToResponse(carParkingViolationRecordService.removeById(id));
    }

    /**
     * 01-查询车辆违停罚款记录
     */

    @GetMapping("/showViolationRecordList")
    public ResultResponse showViolationRecordList(PageVo pageVo) {

        log.info("=> 查询车辆违停罚款记录 <=");

        return carParkingViolationRecordService.showViolationRecordList(pageVo);
    }


    /**
     * 02-查询门店车辆违停罚款数据报表(给到评比 扇形图 改成人数)
     */
    @GetMapping("/showViolationReport")
    public ResultResponse showViolationReport(PageVo pageVo) {

        log.info("=>文明评比 <=");

        return carParkingViolationRecordService.showViolationReport(pageVo);
    }


    /**
     * 01-存储过程快速添加随机违章数据(这里做展示)
     * <p>
     * 建议使用mysql直接执行
     */
    @Deprecated
    public void addViolationRecord() {
        carParkingViolationRecordService.addViolationRecord();
    }

}

