package cn.manager.system.utils;

import cn.manager.common.constant.CacheConstants;
import cn.manager.system.entity.SystemUnifySet;
import cn.manager.system.utils.redis.RedisCacheUtils;
import com.alibaba.fastjson2.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 车辆服务统一工具类
 *
 * @author ljc
 * @version 0.1.0
 * @description: ok
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class CarServiceUtils {

    private final RedisCacheUtils redisCacheUtils;

    /**
     * 获取最大停车数量
     */
    public int getCarMaxParking() {

        //位置1 开放时间 位置2最大车位  位置3 停车费用封顶
        List<SystemUnifySet> unifyCacheList = redisCacheUtils.getCacheObject(CacheConstants.SYSTEM_CAR_SETTINGS);

        SystemUnifySet systemUnifyBaseSet = unifyCacheList.get(1);
        String maxCarParking = systemUnifyBaseSet.getCommonStr1();//最大停车数量
        return Integer.parseInt(maxCarParking);
    }

    /**
     * 获取当前使用的停车位
     */
    public int getCarNowUsedParking() {

        return redisCacheUtils.getCacheObject(CacheConstants.CAR_PARKING_USED_COUNTS);
    }

    /**
     * 校验是否可以存入(最大车位>使用车位 都可以存入汽车)
     * true可以存
     * <p>
     * false不可以存
     */
    public boolean availableParking() {
        //只要最大车位>使用车位 都可以存入汽车
        return getCarMaxParking() > getCarNowUsedParking();
    }


    /**
     * 已使用的车位增加(1)
     * 逻辑是: 这个数越大 剩余车位越少
     */
    public Integer incrCarNowUsedCounts() {

        return Math.toIntExact(redisCacheUtils.incr(CacheConstants.CAR_PARKING_USED_COUNTS));
    }

    //

    /**
     * 已使用车位减少(1)
     * 逻辑是: 这个数越大 剩余车位越多
     */
    public Integer decrCarNowUsedCounts() {

        Long decrCounts = redisCacheUtils.decr(CacheConstants.CAR_PARKING_USED_COUNTS);

        //如果小于0 默认设置为0
        if (decrCounts < 0L) {
            redisCacheUtils.setCacheObject(CacheConstants.CAR_PARKING_USED_COUNTS, "0");
        }
        return Math.toIntExact(decrCounts);
    }


    /**
     * 查询最大停车费用
     */
    public int getMaxParkingPay() {
        List<SystemUnifySet> unifyCacheList = redisCacheUtils.getCacheObject(CacheConstants.SYSTEM_CAR_SETTINGS);
        SystemUnifySet systemUnifyBaseSet = unifyCacheList.get(2);
        String maxParkingPay = systemUnifyBaseSet.getCommonStr();//查询最大停车费用
        return Integer.parseInt(maxParkingPay);
    }

    /**
     * 车辆进出口重复提交锁
     */
    public void carRepeatLock(String plateNumber, int second) {

        redisCacheUtils.setCacheObject(String.format(CacheConstants.CAR_REPEAT_LOCK, plateNumber), "1", second, TimeUnit.SECONDS);
    }


    /**
     * 车辆进出口重复提交锁
     */
    public boolean checkCarRepeatLock(String plateNumber) {

        return redisCacheUtils.hasKey(String.format(CacheConstants.CAR_REPEAT_LOCK, plateNumber));
    }


}
