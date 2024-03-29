package cn.manager.system.utils.redis;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;


/**
 * id分布式算法
 *
 * @author ljc
 * @version 0.1.0
 * @description TODO
 */
@Slf4j
@Component
public class IdAlgorithm {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 年月日自增
     */
    public Long increaseOrderId(String redisKey) {

        //时间位
        DateTimeFormatter DF_FMT_PREFIX = DateTimeFormatter.ofPattern("yyMMdd");
        LocalDateTime dataTime = LocalDateTime.now();
        String dateStr = DF_FMT_PREFIX.format(dataTime);

        String keyPrefix = String.format(redisKey, dateStr);

        //顺序自增位
        Long increment = redisTemplate.opsForValue().increment(keyPrefix);
        if (increment.intValue() == 1) {
            redisTemplate.expire(keyPrefix, 25, TimeUnit.HOURS);
        }
        String orderNo = StringUtils.leftPad(String.valueOf(increment), 5, "0");

        return Long.parseLong(dateStr + orderNo);
    }


    /**
     * 获取long类型的id
     *
     * @param keyName
     * @return
     */
    public Long getLongId(String keyName) {

        return redisTemplate.opsForValue().increment(keyName);
    }


}
