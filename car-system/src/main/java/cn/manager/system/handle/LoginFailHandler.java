package cn.manager.system.handle;

import java.io.IOException;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.manager.common.constant.HttpStatus;
import cn.manager.common.core.ResultResponse;
import cn.manager.common.utils.ServletUtils;
import cn.manager.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson2.JSON;


/**
 * 认证失败处理类 返回未授权 401
 *
 * @author ljc
 * @version 1.0.0
 * @description ok
 */
@Slf4j
@Component
public class LoginFailHandler implements AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {

        int code = HttpStatus.UNAUTHORIZED;

        log.error(StringUtils.format("===> 请求访问：{}，认证失败，无法访问系统资源", request.getRequestURI()));

        ServletUtils.renderString(response, JSON.toJSONString(ResultResponse.error(code, "你好用户,请先登录哟~")));
    }
}
