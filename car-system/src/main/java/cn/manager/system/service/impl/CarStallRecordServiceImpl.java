package cn.manager.system.service.impl;

import cn.manager.common.constant.DictConstants;
import cn.manager.common.core.PageVo;
import cn.manager.common.core.ResultResponse;
import cn.manager.common.dto.request.CarStallRecordReqDto;
import cn.manager.common.dto.response.CarStallRecordRespDTO;
import cn.manager.common.dto.response.StallCompareRespDTO;
import cn.manager.common.enums.ResponseEnum;
import cn.manager.common.exception.ServiceException;
import cn.manager.common.utils.DateUtils;
import cn.manager.common.utils.IdGenerateUtils;
import cn.manager.system.entity.*;
import cn.manager.system.mapper.CarParkingViolationRecordMapper;
import cn.manager.system.mapper.CarStallAppointMapper;
import cn.manager.system.mapper.CarStallRecordMapper;
import cn.manager.system.mapper.CarUserRecordMapper;
import cn.manager.system.service.CarStallRecordService;
import cn.manager.system.utils.CarServiceUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static cn.manager.system.controller.car.CarParkingPayRuleController.carParkingPayCaches;

/**
 * <p>
 * 车辆进出口记录表 服务实现类
 * </p>
 *
 * @author ljc
 * @since 2024-03-17
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CarStallRecordServiceImpl extends ServiceImpl<CarStallRecordMapper, CarStallRecord> implements CarStallRecordService {

    /**
     * 车辆服务操作工具类
     */
    private final CarServiceUtils carServiceUtils;

    /**
     * 用户车辆记录
     */
    private final CarUserRecordMapper carUserRecordMapper;

    /**
     * 车辆预约记录
     */
    private final CarStallAppointMapper carStallAppointMapper;

    private final CarParkingViolationRecordMapper carParkingViolationRecordMapper;

    /**
     * 搜索条件
     * <p>
     * 1. 主键id搜索(单项详情)
     * 2. 进出口类型 1进2出
     * 3. 车牌搜索
     * 4. 进出时间排序
     * 5. 创建时间倒序
     */
    @Override
    public ResultResponse showStallRecordList(PageVo pageVo) {

        IPage<CarStallRecordRespDTO> iPage = new Page<>(pageVo.getPage(), pageVo.getSize());


        QueryWrapper<CarStallRecordRespDTO> queryWrapper = new QueryWrapper<CarStallRecordRespDTO>();

        //查询进出口逻辑
        //进口逻辑
        if (Objects.equals(pageVo.getCommonType(), Integer.parseInt(DictConstants.IN_ACCESS_TYPE))) {
            queryWrapper.in(pageVo.getCommonType() != null, "csr.access_type", DictConstants.IN_ACCESS_TYPE);
            queryWrapper.orderByDesc("csr.create_time");
        } else {
            //出口逻辑
            queryWrapper.in(pageVo.getCommonType() != null, "csr.access_type", DictConstants.OUT_ACCESS_TYPE);
            queryWrapper.orderByDesc("csr.update_time");
        }

        queryWrapper.eq(pageVo.getKeyId() != null, "csr.id", pageVo.getKeyId())
                .between(pageVo.checkBeginEndTime(), "csr.create_time", pageVo.getBeginTime(), pageVo.getEndTime())
                .eq(StringUtils.isNotBlank(pageVo.getSearchName()), "csr.plate_number", pageVo.getSearchName())
        ;

        iPage = baseMapper.showStallRecordList(iPage, queryWrapper);

        return ResultResponse.ok().setData(iPage);
    }

    /**
     * 车辆文明评比
     */
    @Override
    public ResultResponse stallAnalyseCompare(StallCompareRespDTO stallCompareRespDTO, PageVo pageVo) {

        IPage<StallCompareRespDTO> iPage = new Page<>(pageVo.getPage(), pageVo.getSize());

        iPage = baseMapper.selectStallAnalyseCompare(iPage,
                new QueryWrapper<StallCompareRespDTO>().ne("csr.user_id", 0)
                        .eq(stallCompareRespDTO.getRoleId() != null, "su.role_id", stallCompareRespDTO.getRoleId())
                        .between(pageVo.checkBeginEndTime(), "csr.create_time", pageVo.getBeginTime(), pageVo.getEndTime())
                        .groupBy("csr.user_id")
                        .orderByDesc("payTotalMoney")
        );

        return ResultResponse.ok().setData(iPage);
    }

    /**
     * 获取进口还是出口逻辑
     */
    @Override
    public String getAccessInOrOutType(String plateNumber) {
        //00.先判断出是进还是出 accessType 出查2  进查2和1  根据车牌查出最近一次是进还是出( 如果是进 则生成出记录 反之则生成进记录)
        CarStallRecord carStallOldRecord = baseMapper.selectOne(Wrappers.<CarStallRecord>lambdaQuery()
                .eq(CarStallRecord::getPlateNumber, plateNumber)
                .orderByDesc(CarStallRecord::getUpdateTime)
                .last("limit 1")
        );

        /*校验: 如果是空或出口  则为进口*/
        if (carStallOldRecord == null || Objects.equals(carStallOldRecord.getAccessType(), DictConstants.OUT_ACCESS_TYPE)) {
            return DictConstants.IN_ACCESS_TYPE;
        } else {
            return DictConstants.OUT_ACCESS_TYPE;
        }
    }

    @Transactional
    @Override
    public void addStallRecord(CarStallRecordReqDto carStallRecordReqDto) {
        //防重锁
        int carRepeatLockSecond = 30;
        //唯一变量车牌
        String plateNumber = carStallRecordReqDto.getPlateNumber();
        String accessType = carStallRecordReqDto.getAccessType();

        /*校验: 重复提交校验*/
        if (carServiceUtils.checkCarRepeatLock(plateNumber)) {
            throw new ServiceException(ResponseEnum.A10004, String.valueOf(carRepeatLockSecond));
        }

        /*校验: [此处为进口逻辑]*/
        if (Objects.equals(accessType, DictConstants.IN_ACCESS_TYPE)) {
            //进口功能逻辑执行方法
            inAccessMethod(carStallRecordReqDto);
        } else {
            //出口功能逻辑执行方法
            outAccessMethod(plateNumber, carStallRecordReqDto.getOutGarageNum());
        }

        //3.2 加锁防止重复操作
        carServiceUtils.carRepeatLock(plateNumber, carRepeatLockSecond);
    }

    /**
     * 进口功能逻辑执行方法
     *
     * @param carStallRecordReqDto 请求dto
     */
    private void inAccessMethod(CarStallRecordReqDto carStallRecordReqDto) {
        //进口逻辑
        CarStallRecord carStallRecord = new CarStallRecord();
        BeanUtils.copyProperties(carStallRecordReqDto, carStallRecord);

        //0. 先判断是否有车位(没有直接else) - [升级: 可查询本时段的预约列表(也算车位数量)]
        if (!carServiceUtils.availableParking()) {
            throw new ServiceException(ResponseEnum.A10001, "抱歉车位不足,剩余: 0");
        }

        //1. 根据车牌判断是否是正常的用户角色
        CarUserRecord carUserRecord = carUserRecordMapper.selectOne(Wrappers.lambdaQuery(new CarUserRecord().setPlateNumber(carStallRecordReqDto.getPlateNumber())));

        //2. 如果不是正常用户 查看是否有预约(并且审核通过)并且时间符合
        if (carUserRecord == null) {
            CarStallAppoint carStallAppoint = carStallAppointMapper.selectOne(Wrappers.<CarStallAppoint>lambdaQuery()
                    .eq(CarStallAppoint::getPlateNumber, carStallRecordReqDto.getPlateNumber())
                    .orderByDesc(CarStallAppoint::getUpdateTime)
                    .last("limit 1"));

            //用户预约检查
            userAppointCheck(carStallAppoint);

            //设置预约单号
            carStallRecord.setApOrderId(carStallAppoint.getId());

            //2.0 预约记录变成完成(利用事务回滚)
            carStallAppointMapper.updateById(new CarStallAppoint(carStallAppoint.getId(), "3"));
        }

        //2.1设置进出口的人id
        carStallRecord.setUserId(carUserRecord == null ? null : carUserRecord.getUserId());

        //3.设置accessType = 1 进口
        carStallRecord.setAccessType(DictConstants.IN_ACCESS_TYPE);//进口

        carStallRecord.setId(IdGenerateUtils.getInstance().nextId());

        //3.1 插入一条信息(进入), 设置开始计时时间(30秒内不可重复操作)//根据创建时间判断
        baseMapper.insert(carStallRecord);

        //4. 减少车位(已使用车位增加1)
        carServiceUtils.incrCarNowUsedCounts();
    }

    /**
     * 出口功能逻辑执行方法
     *
     * @param plateNumber  车牌
     * @param outGarageNum 大门号
     */
    private void outAccessMethod(String plateNumber, Integer outGarageNum) {
        //1. 查询最近一次数据
        CarStallRecord carStallOldRecord = baseMapper.selectOne(Wrappers.<CarStallRecord>lambdaQuery()
                .eq(CarStallRecord::getPlateNumber, plateNumber)
                .orderByDesc(CarStallRecord::getUpdateTime)
                .last("limit 1")
        );

        //基本不会走此逻辑: 逻辑严谨性---------------------->
        if (carStallOldRecord == null) {
            throw new ServiceException(ResponseEnum.A10001, "抱歉, 未查询到您进入信息!");
        }

        if ("1".equals(carStallOldRecord.getStatus())) {
            throw new ServiceException(ResponseEnum.A10001, "抱歉, 您最近一次操作已完成,请去支付呦~");
        }
        if ("2".equals(carStallOldRecord.getStatus())) {
            throw new ServiceException(ResponseEnum.A10001, "抱歉, 您最近一次操作已完成哟~");
        }
        //------------------------------------------>

        // 2. 数据计算
        // 2.1 计算总时间
        int totalSecond = DateUtils.dateSecondDiff(carStallOldRecord.getCreateTime(), DateUtils.getNowDate());

        log.info("===> 停车总时间: {}", totalSecond);
        //2.2 获取总价格(停车)
        int totalPrice = getTotalPrice(totalSecond);

        //罚款总费用
        int totalViolationPrice = 0;

        LambdaQueryWrapper<CarParkingViolationRecord> lambdaQueryWrapper = Wrappers.<CarParkingViolationRecord>lambdaQuery();

        Long violationCommonId = null;
        if (carStallOldRecord.getUserId() != null) {

            violationCommonId = carStallOldRecord.getUserId();

            lambdaQueryWrapper.eq(CarParkingViolationRecord::getUserId, violationCommonId);
        } else {
            Long apOrderId = carStallOldRecord.getApOrderId();

            lambdaQueryWrapper.eq(CarParkingViolationRecord::getAppointId, apOrderId);
        }

        //创建时间-现在时间内的违章记录
        List<CarParkingViolationRecord> carParkingViolationRecordList = carParkingViolationRecordMapper.selectList(
                lambdaQueryWrapper.eq(CarParkingViolationRecord::getPayStatus, 0)
                        .between(CarParkingViolationRecord::getCreateTime, carStallOldRecord.getCreateTime(), new Date())
        );

        if (CollectionUtils.isNotEmpty(carParkingViolationRecordList)) {
            totalViolationPrice = carParkingViolationRecordList.stream().map(CarParkingViolationRecord::getPenaltyMoney).mapToInt(Integer::intValue).sum();

            //批量修改支付状态
            List<Long> violationId = carParkingViolationRecordList.stream().map(CarParkingViolationRecord::getId).collect(Collectors.toList());
            carParkingViolationRecordMapper.update(null,
                    Wrappers.<CarParkingViolationRecord>lambdaUpdate()
                            .set(CarParkingViolationRecord::getPayStatus, 1)
                            .set(CarParkingViolationRecord::getUpdateTime, new Date())
                            .in(CarParkingViolationRecord::getId, violationId)
            );
        }

        //3. 找到上次进口记录进行修改

        log.info("停车费用: " + totalPrice);
        log.info("违停费用:" + totalViolationPrice);

        baseMapper.updateById(new CarStallRecord().setId(carStallOldRecord.getId())
                .setAccessType(DictConstants.OUT_ACCESS_TYPE)//修改accessType= 2 记录结束时间
                .setOutGarageNum(outGarageNum).setTotalTime(totalSecond) //停车总时间
                .setPayMoney(String.valueOf(totalPrice + totalViolationPrice)) //总金额
                .setStatus("1")//状态(0正常,1已结束未付款 ,2已结束已付款)
        );

        //4. 车位+1
        carServiceUtils.decrCarNowUsedCounts();
    }

    /**
     * 用户预约检查
     */
    private void userAppointCheck(CarStallAppoint carStallAppoint) {

        /*校验 没身份还不预约的 不让进*/
        if (carStallAppoint == null) {
            throw new ServiceException(ResponseEnum.A10001, "抱歉,您未预约,不可进入~");
        }

        /*校验: 如果现在时间在预约截止时间之前 预约有效*/
        if (DateUtils.getNowDate().after(carStallAppoint.getApEndTime())) {
            throw new ServiceException(ResponseEnum.A10001, "抱歉,您预约已过期,不可进入");
        }

        /*校验 没身份还不预约的 不让进*/
        if (!Objects.equals(carStallAppoint.getApStatus(), "1")) {

            if (Objects.equals(carStallAppoint.getApStatus(), "0")) {
                throw new ServiceException(ResponseEnum.A10001, "您的预约正在审核中,请耐心等待~");
            }
            if (Objects.equals(carStallAppoint.getApStatus(), "2")) {
                throw new ServiceException(ResponseEnum.A10001, "不可进入,预约审核未通过,原因: " + carStallAppoint.getRefuseReason());
            }
            if (Objects.equals(carStallAppoint.getApStatus(), "3")) {
                throw new ServiceException(ResponseEnum.A10001, "您的最近一次预约已结束,请重新预约~");
            }
        }
    }

    /**
     * 根据停车规则 动态获取出当前停车费用
     * 14.21小时 按照15小时计算
     * 2小时*0元 + 6小时*2元 +(15-8)*3 元
     *
     * @param totalSecond 停车总秒数
     */
    private int getTotalPrice(int totalSecond) {

        //2 根据收费规则(等级)分组
        Map<Integer, CarParkingPayRule> ruleMap = carParkingPayCaches.stream().collect(Collectors.toMap(CarParkingPayRule::getRuleLevel, Function.identity()));

        //2.1 计算总收费记录
        int totalPrice = 0;//总费用
        int residueSecond = totalSecond;//未结算剩余秒数
        for (int i = 1; i <= ruleMap.keySet().size(); i++) {

            //2.2 判断出等级
            CarParkingPayRule carParkingPayRule = ruleMap.get(i);
            Integer availableMaxParking = carParkingPayRule.getAvailableMaxParking();
            Integer availableMinParking = carParkingPayRule.getAvailableMinParking();
            Integer hourCostMoney = carParkingPayRule.getCostMoney();

            //2.3 当前等级剩余秒数
            int levelAvailableSecond = availableMaxParking - availableMinParking;
            /*校验1: 剩余秒数大于本等级最大秒数 获取出来*/
            if (residueSecond >= levelAvailableSecond) {
                totalPrice += hourCostMoney * DateUtils.convertSecondsToHours(levelAvailableSecond);
                residueSecond = residueSecond - levelAvailableSecond;
            } else { //校验1: 剩余秒数小于本等级最大秒数 直接计算
                totalPrice += hourCostMoney * DateUtils.convertSecondsToHours(residueSecond);
                break;
            }
        }

        log.info("==> 停车费是: " + totalPrice);

        //2.2 保留最小值
        return Math.min(totalPrice, carServiceUtils.getMaxParkingPay());
    }

}
