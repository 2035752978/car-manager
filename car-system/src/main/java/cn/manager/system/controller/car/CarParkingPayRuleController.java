package cn.manager.system.controller.car;

import cn.manager.common.core.ResultResponse;

import cn.manager.common.enums.ResponseEnum;
import cn.manager.common.exception.ServiceException;
import cn.manager.system.entity.CarParkingPayRule;
import cn.manager.system.service.CarParkingPayRuleService;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.sql.rowset.serial.SerialException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

/**
 * <p>
 * 车辆停放费用规则 前端控制器
 * </p>
 *
 * @author ljc
 * @since 2024-03-16
 */
@Slf4j
@RestController
@RequestMapping("/car/carParkRule")
public class CarParkingPayRuleController {

    @Autowired
    private CarParkingPayRuleService carParkingPayRuleService;

    //设置本地缓存
    public final static List<CarParkingPayRule> carParkingPayCaches = new CopyOnWriteArrayList<>();

    /**
     * 01-查询车辆停放费用规则
     */
//    @PreAuthorize("hasPermission('car:payRule:list')")
    @GetMapping("/showParkingPayRule")
    public ResultResponse showParkingPayRule() {

        log.info(" ===> 查询车辆停放费用规则  <===");

        if (CollectionUtils.isEmpty(carParkingPayCaches)) {
            List<CarParkingPayRule> payRules = carParkingPayRuleService.list();
            carParkingPayCaches.addAll(payRules);
        }

        return ResultResponse.ok().setData(carParkingPayCaches);
    }

    /**
     * 02-添加车辆停放费用规则
     */
    @PreAuthorize("hasPermission('car:payRule:add')")
    @PostMapping("/addParkingPayRule")
    public ResultResponse addNotice(@Validated @RequestBody CarParkingPayRule carParkingPayRule) {

        log.info("=> 添加车辆停放费用规则 <=");

        //校验最大支付金额是否符合逻辑
        checkMaxPayRule(true, carParkingPayRule.getRuleLevel(), carParkingPayRule.getAvailableMinParking(), carParkingPayRule.getAvailableMaxParking());

        boolean flag = carParkingPayRuleService.save(carParkingPayRule);

        carParkingPayCaches.add(carParkingPayRule);

        return ResultResponse.booleanToResponse(flag);
    }

    /**
     * 03-修改车辆停放费用规则
     */
    @PreAuthorize("hasPermission('car:payRule:edit')")
    @PutMapping("/editParkingPayRule")
    public ResultResponse editNotice(@RequestBody CarParkingPayRule carParkingPayRule) {

        log.info("=> 修改车辆停放费用规则 <=");


        //校验最大支付金额是否符合逻辑
        checkMaxPayRule(false, carParkingPayRule.getRuleLevel(), carParkingPayRule.getAvailableMinParking(), carParkingPayRule.getAvailableMaxParking());

        boolean flag = carParkingPayRuleService.updateById(carParkingPayRule);

        if (!flag) return ResultResponse.error();

        //刷新缓存
        refreshPayRule();

        return ResultResponse.ok();
    }

    /**
     * 04-刷新(本地缓存)车辆停放费用规则
     */
    @PreAuthorize("hasPermission('car:payRule:refresh')")
    @PutMapping("/refreshPayRule")
    public ResultResponse refreshPayRule() {

        log.info("=> 刷新(本地缓存)车辆停放费用规则 <=");

        //清空list
        carParkingPayCaches.clear();

        //刷新缓存
        return showParkingPayRule();
    }

    /**
     * 05-删除车辆停放费用规则
     */
    @PreAuthorize("hasPermission('car:payRule:delete')")
    @DeleteMapping("/delPayRule/{id}")
    public ResultResponse delNotice(@PathVariable("id") Integer id) {

        log.info("=> 删除车辆停放费用规则 <=");

        boolean flag = carParkingPayRuleService.removeById(id);

        if (!flag) return ResultResponse.error();

        //缓存清除指定元素
        IntStream.range(0, carParkingPayCaches.size())
                .filter(index -> Objects.equals(carParkingPayCaches.get(index).getId(), id)).boxed()
                .findFirst().map(index -> carParkingPayCaches.remove((int) index));


        return ResultResponse.ok().setData(carParkingPayCaches);
    }

    /**
     * 校验最大支付金额是否符合逻辑
     */
    private void checkMaxPayRule(boolean isAdd, Integer level, int minPayRule, int maxPayRule) {

        /*校验: 最小值大于等于最大值*/
        if (minPayRule >= maxPayRule) {

            throw new ServiceException(ResponseEnum.A10001, "抱歉, 可用最小时间不得大于最大可用时间");
        }

        //去掉 减少校验
//        CarParkingPayRule parkOldRule = carParkingPayRuleService.getOne(Wrappers.<CarParkingPayRule>lambdaQuery()
//                .orderByDesc(CarParkingPayRule::getAvailableMinParking).last("limit 1"));
//
//        if (parkOldRule != null) {
//            //ps: 如果使用== 注意整数池问题 (不是新增 不是最大级, 如果传入值小于最大值 则报错)
//            if (!isAdd && !Objects.equals(level, parkOldRule.getRuleLevel()) && maxPayRule < parkOldRule.getAvailableMinParking()) {
//                throw new ServiceException(ResponseEnum.A10001, "抱歉, 可用最大时间不得小于" + parkOldRule.getRuleLevel() + "等级的最大值");
//            }
//        }
    }


}


