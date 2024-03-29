package cn.manager.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 车辆进出口记录表
 * </p>
 *
 * @author ljc
 * @since 2024-03-17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class CarStallRecord extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.INPUT)
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
     * 出大门编号
     */
    private Integer outGarageNum;

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

}
