package cn.manager.system.service;

import cn.manager.common.core.PageVo;
import cn.manager.common.core.ResultResponse;
import cn.manager.system.entity.CarParkingViolationRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 车辆违停罚款记录表 服务类
 * </p>
 *
 * @author ljc
 * @since 2024-03-19
 */
public interface CarParkingViolationRecordService extends IService<CarParkingViolationRecord> {

    void addViolationRecord();

    ResultResponse showViolationRecordList(PageVo pageVo);

    ResultResponse showViolationReport(PageVo pageVo);

}
