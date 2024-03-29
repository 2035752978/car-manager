/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package cn.manager.common.core;


import cn.manager.common.constant.HttpStatus;
import cn.manager.common.utils.StringUtils;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;


import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 *
 * @author ljc
 * @description ok
 * @version 1.0.0
 */
public class ResultResponse extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public ResultResponse() {
        put("code", 200);
        put("msg", "success");
    }

    //利用alibaba提供的fastjson进行逆转
    public <T> T getData(String key, TypeReference<T> typeReference) {
        //默认是map
        Object data = get(key);

        String s = JSON.toJSONString(data);
        T t = JSON.parseObject(s, typeReference);
        return t;
    }

    public <T> T getData(TypeReference<T> typeReference) {
        Object data = get("data");
        String s = JSON.toJSONString(data);
        T t = JSON.parseObject(s, typeReference);
        return t;
    }

    public String getData() {
        Object data = get("data");
        String s = JSON.toJSONString(data);
        return s;
    }

    public ResultResponse setData(Object o) {
        put("data", o);
        return this;
    }

    /**
     * 部分历史业务用list 并且修改 前端修改位置过多, 启用新字段
     *
     * @param o
     * @return
     */
    public ResultResponse setTotal(Object o) {
        put("total", o);
        return this;
    }

    public Object getData(Object o) {
        return get(o);
    }


    public static ResultResponse error() {
        return error(HttpStatus.ERROR, "未知异常，请联系管理员");
    }

    public static ResultResponse error(String msg) {
        return error(HttpStatus.ERROR, msg);
    }

    public static ResultResponse error(int code, String msg) {
        ResultResponse r = new ResultResponse();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static ResultResponse error(String code, String msg) {
        ResultResponse r = new ResultResponse();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static ResultResponse ok(String msg) {
        ResultResponse r = new ResultResponse();
        r.put("msg", msg);
        return r;
    }


    public static ResultResponse ok(Map<String, Object> map) {
        ResultResponse r = new ResultResponse();
        r.putAll(map);
        return r;
    }

    public static ResultResponse ok() {
        return new ResultResponse();
    }

    @Override
    public ResultResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public Integer getCode() {
        return (Integer) this.get("code");
    }

    public String getMsg() {
        return (String) this.get("msg");
    }


    /**
     * (完整版)快速校验返回成功还是失败
     *
     * @param flag       true 成功  false失败
     * @param successMsg 成功信息
     * @param failMsg    失败信息
     * @return ResultResponse
     */
    public static ResultResponse booleanToResponse(boolean flag, String successMsg, String failMsg) {

        if (StringUtils.isBlank(successMsg)) {
            successMsg = "操作成功!";
        }
        if (StringUtils.isBlank(failMsg)) {
            successMsg = "操作失败!";
        }

        return flag ? ok(successMsg) : error(failMsg);
    }

    /**
     * (简化版)快速校验返回成功还是失败
     *
     * @param flag true 成功  false失败
     * @return ResultResponse
     */
    public static ResultResponse booleanToResponse(boolean flag) {

        return flag ? ok("操作成功!") : error("操作失败!");
    }
}
