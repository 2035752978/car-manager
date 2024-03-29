package cn.manager.common.constant;

/**
 * 缓存的key 常量
 *
 * @author ljc
 * @version 1.0.0
 * @description ok
 */
public class CacheConstants {
    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "car_login_tokens:";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 系统(车辆)设置
     */
    public static final String SYSTEM_CAR_SETTINGS = "system:max_car_parking";


    /**
     * 车位使用数量
     */
    public static final String CAR_PARKING_USED_COUNTS = "system:car_parking_used_counts";

    /**
     * 车位预约key
     */
    public static final String CAR_PARKING_APPOINT = "system:car_appoint";

    /**
     * 车辆重复提交锁
     */
    public static final String CAR_REPEAT_LOCK = "system:car_repeat_lock:%s";

}
