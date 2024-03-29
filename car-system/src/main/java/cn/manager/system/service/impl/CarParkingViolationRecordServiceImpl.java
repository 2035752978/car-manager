package cn.manager.system.service.impl;

import cn.manager.common.core.PageVo;
import cn.manager.common.core.ResultResponse;
import cn.manager.common.dto.response.ViolationReportReqDTO;
import cn.manager.common.utils.DateUtils;
import cn.manager.common.utils.StringUtils;
import cn.manager.system.entity.CarParkingViolationRecord;
import cn.manager.system.mapper.CarParkingViolationRecordMapper;
import cn.manager.system.service.CarParkingViolationRecordService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.awt.geom.QuadCurve2D;
import java.util.List;

/**
 * <p>
 * 车辆违停罚款记录表 服务实现类
 * </p>
 *
 * @author ljc
 * @since 2024-03-16
 */
@Service
public class CarParkingViolationRecordServiceImpl extends ServiceImpl<CarParkingViolationRecordMapper, CarParkingViolationRecord> implements CarParkingViolationRecordService {

    /**
     * 建议mysql使用
     * 使用方法:
     * 先删
     * TRUNCATE car_parking_violation_record;
     * 再填
     * call AddRandomParkingViolation();
     */
    @Deprecated
    @Override
    public void addViolationRecord() {
        baseMapper.addViolationRecordBatch();
    }

    /**
     * 02-查询车辆违停罚款记录
     */
    @Override
    public ResultResponse showViolationRecordList(PageVo pageVo) {

        IPage<CarParkingViolationRecord> iPage = new Page<>(pageVo.getPage(), pageVo.getSize());

        iPage = baseMapper.selectViolationRecordList(iPage, new QueryWrapper<CarParkingViolationRecord>()
                .eq(StringUtils.isNotBlank(pageVo.getSearchName()), "apvr.plate_number", pageVo.getSearchName())
                .between(pageVo.checkBeginEndTime(), "apvr.create_time", pageVo.getBeginTime(), pageVo.getEndTime())
                .eq(pageVo.getRelationId() != null, "su.role_id", pageVo.getRelationId())
                .orderByDesc("apvr.create_time"));

        return ResultResponse.ok().setData(iPage);
    }

    @Override
    public ResultResponse showViolationReport(PageVo pageVo) {

        IPage<ViolationReportReqDTO> iPage = new Page<>(pageVo.getPage(), pageVo.getSize());

        iPage = baseMapper.showViolationReport(iPage, new QueryWrapper<ViolationReportReqDTO>()
                .between(pageVo.checkBeginEndTime(), "apvr.create_time", pageVo.getBeginTime(), pageVo.getEndTime())
                .eq(pageVo.getRelationId() != null, "sd.dept_id", pageVo.getRelationId())
                .isNotNull("apvr.user_id")
                .groupBy("sd.dept_id")
        );

        return ResultResponse.ok().setData(iPage);
    }


}
