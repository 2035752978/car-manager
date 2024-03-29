package cn.manager.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author ljc
 * @version 0.0.1
 * @description TODO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class StallCompareRespDTO {

    /**
     * 停车总时长
     */
    private String parkTotalTime;

    /**
     * 停车总费用
     */
    private String payTotalMoney;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 部门id
     */
    private String deptId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 用户真实姓名
     */
    private String realName;

    /**
     * 电话号
     */
    private String phone;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 角色名称
     */
    private String roleName;
}
