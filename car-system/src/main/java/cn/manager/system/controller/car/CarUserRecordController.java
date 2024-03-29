package cn.manager.system.controller.car;

import cn.manager.common.core.ResultResponse;
import cn.manager.common.utils.IdGenerateUtils;
import cn.manager.system.entity.CarUserRecord;
import cn.manager.system.service.CarUserRecordService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户车牌关系表 (不需要权限参与)
 * </p>
 *
 * @author ljc
 * @since 2024-03-19
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/carUserRecord")
public class CarUserRecordController {

    private final CarUserRecordService carUserRecordService;

    /**
     * 01-添加车牌
     */
    @PostMapping("/addPlateNumber")
    public ResultResponse addPlateNumber(@RequestBody CarUserRecord carUserRecord) {

        log.info("=> 添加车牌 <=");

        try {
            carUserRecordService.save(carUserRecord.setId(IdGenerateUtils.getInstance().nextId()));
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
        }

        return ResultResponse.ok("操作成功!");
    }

    /**
     * 02-查询车牌
     */
    @GetMapping("/showPostList/{userId}")
    public ResultResponse showPlateNumber(@PathVariable("userId") Long userId) {

        log.info("=> 查询车牌 <=");

        List<CarUserRecord> list = carUserRecordService.list(Wrappers.lambdaQuery(new CarUserRecord().setUserId(userId)));

        return ResultResponse.ok().setData(list);
    }

    /**
     * 03-删除车牌
     */
    @DeleteMapping("/del/{id}")
    public ResultResponse del(@PathVariable("id") Long id) {

        log.info("=> 删除车牌 <=");

        return ResultResponse.booleanToResponse(carUserRecordService.removeById(id));
    }

}

