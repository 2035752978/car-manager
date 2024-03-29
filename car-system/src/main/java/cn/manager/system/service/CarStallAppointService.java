package cn.manager.system.service;

import cn.manager.system.entity.CarStallAppoint;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 车辆预约记录表 服务类
 * </p>
 *
 * @author ljc
 * @since 2024-03-19
 */
public interface CarStallAppointService extends IService<CarStallAppoint> {

    void addAppoint(CarStallAppoint carStallAppoint);
}
