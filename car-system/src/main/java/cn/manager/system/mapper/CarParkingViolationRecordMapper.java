package cn.manager.system.mapper;

import cn.manager.common.dto.response.ViolationReportReqDTO;
import cn.manager.system.entity.CarParkingViolationRecord;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 车辆违停罚款记录表 Mapper 接口
 * </p>
 *
 * @author ljc
 * @since 2024-03-18
 */
public interface CarParkingViolationRecordMapper extends BaseMapper<CarParkingViolationRecord> {

    /**
     * 生成随机100条数据
     * <p>
     * 存储过程语句如下
     * <p>
     * CREATE DEFINER=`root`@`%` PROCEDURE `AddRandomParkingViolation`()
     * BEGIN
     *   DECLARE i INT DEFAULT 0;
     *   DECLARE no_parking_area INT;
     *   DECLARE plate_number VARCHAR(50);
     *   DECLARE user_id INT;
     *   DECLARE penalty_money INT;
     *   DECLARE create_time DATETIME;
     *   DECLARE create_by VARCHAR(255);
     *   DECLARE update_time DATETIME;
     *   DECLARE update_by VARCHAR(255);
     *   DECLARE pay_status INT;
     *   DECLARE remark VARCHAR(255);
     *
     *   WHILE i < 100 DO
     *     SET no_parking_area = FLOOR(1 + (RAND() * 5));
     *     SET user_id = FLOOR(3 + (RAND() * 6));
     *     SET penalty_money = FLOOR(10 + (RAND() * 191));
     *     SET create_time = DATE_ADD(  DATE_ADD('2022-01-01 00:00:00',  INTERVAL  FLOOR(1 + (RAND() * 86400))   SECOND ),
     * INTERVAL  FLOOR(1 + (RAND() * 800))  DAY);
     *     SET create_by = (select real_name from system_user where system_user.user_id= user_id);
     *     SET update_time = DATE_ADD(create_time, INTERVAL FLOOR(0 + (RAND() * 86400)) SECOND);
     *     SET update_by = 'admin';
     *     SET pay_status = FLOOR(RAND() * 2);
     *     SET remark = null;
     *     SET plate_number = (select car_user_record.plate_number from car_user_record where car_user_record.user_id = user_id limit 1);
     *
     *     INSERT INTO `car_parking_violation_record` (
     *       `no_parking_area`,
     *       `plate_number`,
     *       `user_id`,
     *       `penalty_money`,
     *       `create_time`,
     *       `create_by`,
     *       `update_time`,
     *       `update_by`,
     *       `pay_status`,
     *       `remark`
     *     ) VALUES (
     *       no_parking_area,
     *       plate_number,
     *       user_id,
     *       penalty_money,
     *       create_time,
     *       create_by,
     *       update_time,
     *       update_by,
     *       pay_status,
     *       remark
     *     );
     *
     *     SET i = i + 1;
     *   END WHILE;
     * END
     */
    @Select("call AddRandomParkingViolation()")
    int addViolationRecordBatch();


    IPage<CarParkingViolationRecord> selectViolationRecordList(IPage<CarParkingViolationRecord> iPage,@Param(Constants.WRAPPER) QueryWrapper<CarParkingViolationRecord> queryWrapper);


    IPage<ViolationReportReqDTO> showViolationReport(IPage<ViolationReportReqDTO> iPage,@Param(Constants.WRAPPER) QueryWrapper<ViolationReportReqDTO> queryWrapper);

}
