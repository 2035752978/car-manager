package cn.manager.common.core;

import cn.manager.common.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 分页统一参数
 * @author ljc
 * @version 0.1.0
 * @description: ok
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
public class PageVo extends CommonReqVo {

    /**
     * 状态
     */
    private Integer status;

    /**
     * 模糊搜索字段
     */
    private String searchName;

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginTime;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    /**
     * 公共分类
     */
    private Integer commonType;

    /**
     * 主键id
     */
    private Long keyId;

    /**
     * 关系表主键id
     */
    private Long relationId;

    /**
     * 车牌搜索
     */
    private String plateNumber;

    /**
     * 时间搜索 解决空指针 筛选时间不正确问题
     */
    public Date getEndTime() {
        return endTime == null ? null : DateUtils.getDateDetail(endTime);
    }

    /**
     * 检查开始时间和结束时间是否是null
     *
     * @return true 不是null false是null
     */
    public boolean checkBeginEndTime() {

        return beginTime != null && endTime != null;
    }
}
