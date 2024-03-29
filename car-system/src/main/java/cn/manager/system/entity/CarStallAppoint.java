package cn.manager.system.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 车辆预约记录表
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
public class CarStallAppoint extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 预约单号(展示用)
     */
    private String apOrderNum;

    /**
     * 车牌号
     */
    @NotBlank(message = "车牌不可为空!")
    private String plateNumber;

    /**
     * 电话号
     */
    @NotBlank(message = "电话号不可为空!")
    private String phone;

    /**
     * 用户姓名
     */
    @NotBlank(message = "用户姓名不能为空!")
    private String realName;

    /**
     * 身份证号
     */
    private String idCardNo;

    /**
     * 预约开始时间
     */
    @NotNull(message = "预约开始时间不能为空!")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date apBeginTime;

    /**
     * 预约结束时间
     */
    @NotNull(message = "预约结束时间不能为空!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date apEndTime;


    /**
     * 预约状态(0 待审核 1审核通过 2审核拒绝  3已结束)
     */
    private String apStatus;

    /**
     * 拒绝原因
     */
    private String refuseReason;

    /**
     * 创建日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @TableField(fill = FieldFill.INSERT)
    private Date createDay;


    public CarStallAppoint(Long id, String apStatus) {
        this.id = id;
        this.apStatus = apStatus;
    }
}
