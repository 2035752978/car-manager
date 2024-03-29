package cn.manager.system.service;

import cn.manager.common.dto.request.LoginReqDTO;

import java.util.Map;

/**
 * @author ljc
 * @version 0.1.0
 * @description ok
 */
public interface LoginService {

    Map<String, Object> login(LoginReqDTO LoginReqDTO);

}
