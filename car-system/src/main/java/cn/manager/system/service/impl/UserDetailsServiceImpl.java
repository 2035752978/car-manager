package cn.manager.system.service.impl;


import cn.manager.common.constant.DictConstants;
import cn.manager.common.enums.ResponseEnum;
import cn.manager.common.exception.ServiceException;
import cn.manager.system.config.security.context.AuthenticationContextHolder;
import cn.manager.system.entity.SystemMenu;
import cn.manager.system.entity.SystemRoleMenu;
import cn.manager.system.mapper.SystemMenuMapper;
import cn.manager.system.mapper.SystemRoleMenuMapper;
import cn.manager.system.mapper.SystemUserMapper;
import cn.manager.system.utils.SecurityUtils;
import cn.manager.system.entity.LoginUser;
import cn.manager.system.entity.SystemUser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 用户验证处理
 *
 * @author ljc
 * @version 1.0.0
 * @description ok
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final SystemUserMapper systemUserMapper;

    private final SystemMenuMapper systemMenuMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SystemUser systemUser = systemUserMapper.selectOne(Wrappers.<SystemUser>lambdaQuery().eq(SystemUser::getUsername, username));

        /*校验: 用户不存在*/
        if (systemUser == null) {
            throw new ServiceException(ResponseEnum.A10003);
        }


        Authentication usernamePasswordAuthenticationToken = AuthenticationContextHolder.getContext();
        String password = usernamePasswordAuthenticationToken.getCredentials().toString();


        /*校验: 密码是否正确 不正确抛异常*/
        boolean flag = matches(password, systemUser.getPassword());
        if (!flag) {
            throw new ServiceException(ResponseEnum.A10002);
        }

        return createLoginUser(systemUser);
    }

    public UserDetails createLoginUser(SystemUser user) {
        return new LoginUser(user.getUserId(), user.getRoleId(), user, getMenuPermission(user));
    }

    /**
     * 密码校验
     *
     * @param rawPassword    用户输入密码
     * @param encodePassword 数据库加密密码
     * @return true 相同
     */
    public boolean matches(String rawPassword, String encodePassword) {
        return SecurityUtils.matchesPassword(rawPassword, encodePassword);
    }

    /**
     * 密码加密返回串
     *
     * @param rawPassword
     * @return
     */
    public String encodePassword(String rawPassword) {
        return SecurityUtils.encodePassword(rawPassword);
    }


    /**
     * 获取菜单数据权限
     *
     * @param systemUser 用户信息
     * @return 菜单权限信息
     */
    public Set<String> getMenuPermission(SystemUser systemUser) {

        /*校验: 如果是超级管理员*/
        if (Objects.equals(systemUser.getUserId(), 1L)) {

            return systemMenuMapper.selectList(
                    Wrappers.lambdaQuery(
                            new SystemMenu(String.valueOf(DictConstants.NORMAL_STATUS)))
            ).stream().map(SystemMenu::getPerms).filter(StringUtils::isNotBlank).collect(Collectors.toSet());

        } else {
            if (systemUser.getRoleId() == null) {
                throw new ServiceException(ResponseEnum.A10001, systemUser.getRealName() + " 请联系管理员分配角色");
            }
            //没查出数据就说明角色没分配权限
            List<String> paramsList = systemMenuMapper.selectParamsByRole(systemUser);

            return paramsList.stream().filter(StringUtils::isNotBlank).collect(Collectors.toSet());
        }

    }

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("123"));

        System.out.println(bCryptPasswordEncoder.matches("123", "$2a$10$dRwEql6K5Df1a6SV7ZpHCuDzk32oPtHx/UOTnpPwr0BnhdXTvoDeC"));

    }

}
