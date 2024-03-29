package cn.manager.common.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author ljc
 * @version 0.1.0
 * @description: TODO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ViolationReportReqDTO {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createDay;
    /**
     * 部门id
     */
    private String deptId;

    /**
     * 总罚款钱数
     */
    private String penaltyTotalMoney;

    /**
     * 违章次数
     */
    private String value;

    /**
     * 部门名
     */
    private String name;
}
