package cn.manager.system.service.impl;


import cn.manager.common.dto.request.LoginReqDTO;
import cn.manager.common.enums.ResponseEnum;
import cn.manager.common.exception.ServiceException;
import cn.manager.common.utils.StringUtils;
import cn.manager.system.config.security.context.AuthenticationContextHolder;
import cn.manager.system.entity.LoginUser;
import cn.manager.system.entity.SystemUser;
import cn.manager.system.mapper.SystemUserMapper;
import cn.manager.system.service.TokenService;
import cn.manager.system.service.LoginService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 实现类
 *
 * @author ljc
 * @version 0.1.0
 * @description ok
 */
@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {

    private final TokenService tokenService;

    private final AuthenticationManager authenticationManager;

    private final SystemUserMapper systemUserMapper;


    /**
     * 登录验证
     */
    @Override
    public Map<String, Object> login(LoginReqDTO LoginReqDTO) {

        String username = LoginReqDTO.getUsername();
        String password = LoginReqDTO.getPassword();

        // 登录前置校验
        loginCheck(username, password);

        // 用户验证
        Authentication authentication = null;
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            AuthenticationContextHolder.setContext(authenticationToken);
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof BadCredentialsException) {
                throw new ServiceException(ResponseEnum.A10002);
            } else {
                throw new ServiceException(e.getMessage());
            }
        } finally {
            AuthenticationContextHolder.clearContext();
        }

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        //修改登录时间
        recordLoginInfo(loginUser.getUserId());

        //获取权限树
        Map<String, Object> map = new HashMap<>();

        map.put("token", tokenService.createToken(loginUser));
        map.put("userRole", String.valueOf(loginUser.getRoleId()));
//        map.put("userId", String.valueOf(loginUser.getUserId()));

        // 生成token
        return map;
    }

    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Long userId) {

        systemUserMapper.update(null, Wrappers.<SystemUser>lambdaUpdate()
                .set(SystemUser::getLoginDate, new Date())
                .eq(SystemUser::getUserId, userId)
        );


        //更新最近登录时间
    }

    /**
     * 登录前置校验
     *
     * @param username 用户名
     * @param password 用户密码
     */
    public void loginCheck(String username, String password) {
        // 用户名或密码为空 错误
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new ServiceException(ResponseEnum.A10002);
        }
    }


}
