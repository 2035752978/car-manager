package cn.manager.common.dto.response;

import java.util.Date;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * 车辆进出口记录响应dto
 *
 * @author ljc
 * @since 2024-03-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class CarStallRecordRespDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 进出状态(1进 2出)  出查2  进查2和1
     */
    private String accessType;

    /**
     * 进大门编号
     */
    private Integer inGarageNum;

    /**
     * 进大门名称
     */
    private String inGarageName;
    /**
     * 出大门编号
     */
    private Integer outGarageNum;

    /**
     * 出大门编号名称
     */
    private String outGarageName;


    /**
     * 车牌
     */
    private String plateNumber;

    /**
     * 用户id(没有不填)
     */
    private Long userId;

    /**
     * 预约id
     */
    private Long apOrderId;


    /**
     * 状态(0正常,1已结束未付款 ,2已结束已付款)
     */
    private String status;


    /**
     * 总时间(秒,最高68年)
     */
    private Integer totalTime;

    /**
     * 需要缴纳费用
     */
    private String payMoney;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 创建人
     */
    private String createBy;


    /**
     * 更新时间(最近一次分享时间)
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 备注
     */
    private String remark;

    private String realName;

    private String phone;

    private String apOrderNum;

    private String apPhone;

    private String apRealName;

}
