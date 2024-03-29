package cn.manager.system.config;

import cn.manager.system.controller.car.CarParkingPayRuleController;
import cn.manager.system.controller.manager.SystemUnifySetController;
import cn.manager.system.mapper.SystemUserMapper;
import cn.manager.system.service.SystemUnifySetService;
import com.alibaba.fastjson2.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static cn.manager.system.controller.car.CarParkingPayRuleController.carParkingPayCaches;

/**
 * 类加载时自动执行
 *
 * @author ljc
 * @version 1.0.0
 * @description ok
 */
@Order(1)
@Slf4j
@RequiredArgsConstructor
@Component
public class CommandAutoRunner implements CommandLineRunner {

    private final SystemUserMapper systemUserMapper;

    /**
     * 停车费用规则
     */
    private final CarParkingPayRuleController carParkingPayRuleController;

    /**
     * 系统配置(车位管理)
     */
    private final SystemUnifySetService systemUnifySetService;

    @Override
    public void run(String... args) throws Exception {

        //1. 使用Hikari连接池(单例)对象------------------>
        log.info("=> 使用Hikari连接池(单例)对象 ");

        //懒汉式 会导致第一次使用很慢(节省资源)
        //ps: connectionTestQuery不推荐使用
        systemUserMapper.initialHikariPool();

        log.info("===> HikariPool is created and used successfully");

        //2. 车辆停放费用规则缓存初始化--------------------->
        carParkingPayRuleController.showParkingPayRule();
        log.info("===> 车辆停放费用规则:{}", JSONObject.toJSONString(carParkingPayCaches));


        //3.刷新系统配置缓存--------------------->
        systemUnifySetService.refreshUnifySet();


    }
}
