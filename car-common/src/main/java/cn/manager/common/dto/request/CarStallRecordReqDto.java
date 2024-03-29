package cn.manager.common.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @author ljc
 * @version 0.0.1
 * @description TODO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CarStallRecordReqDto {

    private static final long serialVersionUID = 1L;

    /**
     * 进出状态(1进 2出)  出查2  进查2和1
     */
    @NotBlank(message = "抱歉, 车辆进出状态信息为空!")
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
    @NotBlank(message = "车牌不能为空!")
    private String plateNumber;


    /**
     * 预约id
     */
    private Long apOrderId;

}
