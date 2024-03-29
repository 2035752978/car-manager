package cn.manager.common.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * 登录请求dto
 *
 * @author ljc
 * @version 0.1.0
 * @description ok
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class LoginReqDTO {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不可为空!")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不可为空!")
    private String password;


}
