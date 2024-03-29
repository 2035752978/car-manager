package cn.manager.system.service.impl;

import cn.manager.common.constant.CacheConstants;
import cn.manager.common.enums.ResponseEnum;
import cn.manager.common.exception.ServiceException;
import cn.manager.system.utils.redis.IdAlgorithm;
import cn.manager.common.utils.IdGenerateUtils;
import cn.manager.system.entity.CarStallAppoint;
import cn.manager.system.entity.CarUserRecord;
import cn.manager.system.mapper.CarStallAppointMapper;
import cn.manager.system.mapper.CarUserRecordMapper;
import cn.manager.system.service.CarStallAppointService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 车辆预约记录表 服务实现类
 * </p>
 *
 * @author ljc
 * @since 2024-03-16
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CarStallAppointServiceImpl extends ServiceImpl<CarStallAppointMapper, CarStallAppoint> implements CarStallAppointService {

    private final CarUserRecordMapper carUserRecordMapper;

    private final IdAlgorithm idAlgorithm;


    @Override
    public void addAppoint(CarStallAppoint carStallAppoint) {

        String plateNumber = carStallAppoint.getPlateNumber();
        Integer count = carUserRecordMapper.selectCount(Wrappers.lambdaQuery(new CarUserRecord().setPlateNumber(plateNumber)));
        if (count > 0) {
            throw new ServiceException(ResponseEnum.A10001, "您是校内教职工,无需预约~");
        }
        carStallAppoint.setApOrderNum("AP" + idAlgorithm.increaseOrderId(CacheConstants.CAR_PARKING_APPOINT));

        carStallAppoint.setId(IdGenerateUtils.getInstance().nextId());

        try {
            baseMapper.insert(carStallAppoint);
        } catch (DuplicateKeyException e) {
            log.warn("数据重复: =>{}", e.getCause().getMessage());
            throw new ServiceException(ResponseEnum.A10001, plateNumber + " 今日已经预约了呦~");
        }
    }
}
