package cn.manager.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 车辆停放费用规则(不过多处理)
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
public class CarParkingPayRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 规则等级
     */
    private Integer ruleLevel;

    /**
     * 规则信息
     */
    private String ruleMsg;

    /**
     * 可用最小时间
     */
    private Integer availableMinParking;

    /**
     * 可用最大时间(最大68年)
     */
    private Integer availableMaxParking;

    /**
     * 消费金额(元整)/小时
     */
    private Integer costMoney;


}
