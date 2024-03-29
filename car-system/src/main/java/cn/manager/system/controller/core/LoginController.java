package cn.manager.system.controller.core;

import cn.manager.common.core.PageVo;
import cn.manager.common.core.ResultResponse;
import cn.manager.common.dto.request.LoginReqDTO;
import cn.manager.system.service.LoginService;
import cn.manager.system.service.SystemMenuService;
import com.alibaba.fastjson2.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;

/**
 * 登录控制器
 *
 * @author ljc
 * @version 0.1.0
 * @description ok
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class LoginController {

    private final LoginService loginService;

    private final SystemMenuService systemMenuService;

    /**
     * 登录方法
     *
     * @param LoginReqDTO 请求参数
     * @return 结果
     */
    @PostMapping("/login")
    public ResultResponse login(@Validated @RequestBody LoginReqDTO LoginReqDTO) {

        Map<String, Object> map = loginService.login(LoginReqDTO);

        map.put("menuList", systemMenuService.showMenuList(true, new PageVo().setKeyId(Long.valueOf((String) map.get("userRole")))));

        return ResultResponse.ok().setData(map);
    }

}
