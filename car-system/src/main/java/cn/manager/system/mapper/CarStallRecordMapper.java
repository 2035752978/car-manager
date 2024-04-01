package cn.manager.system.mapper;

import cn.manager.common.dto.response.CarStallRecordRespDTO;
import cn.manager.common.dto.response.StallCompareRespDTO;
import cn.manager.system.entity.CarStallRecord;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 车辆进出口记录表 Mapper 接口
 * </p>
 *
 * @author ljc
 * @since 2024-03-19
 */
@Mapper
public interface CarStallRecordMapper extends BaseMapper<CarStallRecord> {

    int getUsedParking();

    IPage<CarStallRecordRespDTO> showStallRecordList(IPage<CarStallRecordRespDTO> iPage, @Param(Constants.WRAPPER) QueryWrapper<CarStallRecordRespDTO> queryWrapper);

    IPage<StallCompareRespDTO> selectStallAnalyseCompare(IPage<StallCompareRespDTO> iPage, @Param(Constants.WRAPPER) QueryWrapper<StallCompareRespDTO> queryWrapper);
}
