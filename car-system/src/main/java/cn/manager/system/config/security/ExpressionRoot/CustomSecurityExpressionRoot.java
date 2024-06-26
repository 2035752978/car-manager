package cn.manager.system.config.security.ExpressionRoot;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;

/**
 * MethodSecurityExpressionRoot
 * 自定义校验逻辑
 *
 * @author ljc
 * @description ok
 * @version 1.0.0
 */
public class CustomSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private Object filterObject;
    private Object returnObject;
    private AntPathMatcher antPathMatcher = new AntPathMatcher();


    public CustomSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    /**
     * 判断当前对象是否具备某一个权限(自动调用)
     *
     * @param permission
     * @return
     */
    public boolean hasPermission(String permission) {
        //获取当前登录用户所具有的权限
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            if (antPathMatcher.match(authority.getAuthority(), permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否具备多个权限中的任意一个权限
     *
     * @param permissions
     * @return
     */
    public boolean hasAnyPermissions(String... permissions) {
        if (permissions == null || permissions.length == 0) {
            return false;
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            for (String permission : permissions) {
                if (antPathMatcher.match(authority.getAuthority(), permission)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasAllPermissions(String... permissions) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (permissions == null || permissions.length == 0) {
            return false;
        }
        for (String permission : permissions) {
            boolean flag = false;
            for (GrantedAuthority authority : authorities) {
                if (antPathMatcher.match(authority.getAuthority(), permission)) {
                    flag = true;
                }
            }
            if (!flag) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return returnObject;
    }

    @Override
    public Object getThis() {
        return this;
    }
}
