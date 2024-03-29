package cn.manager.system.service;

import cn.manager.common.core.PageVo;
import cn.manager.common.core.ResultResponse;
import cn.manager.common.dto.request.CarStallRecordReqDto;
import cn.manager.common.dto.response.StallCompareRespDTO;
import cn.manager.system.entity.CarStallRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 车辆进出口记录表 服务类
 * </p>
 *
 * @author ljc
 * @since 2024-03-15
 */
public interface CarStallRecordService extends IService<CarStallRecord> {

    ResultResponse showStallRecordList( PageVo pageVo);

    /**
     * 获取进出口类型
     */
    String getAccessInOrOutType(String plateNumber);

    void addStallRecord(CarStallRecordReqDto carStallRecordReqDto);

    ResultResponse stallAnalyseCompare(StallCompareRespDTO stallCompareRespDTO,PageVo pageVo);

}
