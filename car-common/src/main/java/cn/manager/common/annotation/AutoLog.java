package cn.manager.common.annotation;

import org.springframework.core.annotation.AliasFor;

/**
 * 日志抓取(拓展)
 *
 * @author ljc
 * @version 0.0.1
 * @description TODO
 */
public @interface AutoLog {

    @AliasFor("message")
    String value() default "";

    @AliasFor("value")
    String message() default "";

}
