package cn.manager.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * 车辆违停罚款记录表
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
public class CarParkingViolationRecord extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 禁停区域(1-5)
     */
    private Integer noParkingArea;

    /**
     * 车牌
     */
    @NotBlank(message = "车牌不能为空!")
    private String plateNumber;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 预约id
     */
    private Long appointId;

    /**
     * 支付状态(0未支付 1已支付)
     */
    private Integer payStatus;

    /**
     * 罚款(元整)
     */
    private Integer penaltyMoney;

    /**
     * 用户姓名
     */
    @TableField(exist = false)
    private String realName;

    /**
     * 部门id
     */
    @TableField(exist = false)
    private String deptId;

    /**
     * 部门名称
     */
    @TableField(exist = false)
    private String deptName;

    /**
     * 角色id
     */
    @TableField(exist = false)
    private String roleId;

    /**
     * 禁停区名称
     */
    @TableField(exist = false)
    private String dictName;

}
