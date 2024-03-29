package cn.manager.common.exception;


import cn.manager.common.enums.ResponseEnum;

/**
 * 业务异常(统一)
 *
 * @author ljc
 * @version 0.1.0
 * @description ok
 */
public final class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示
     */
    private String message;

    /**
     * 错误明细
     */
    private String detailMessage;

    /**
     * 空构造方法，避免反序列化问题
     */
    public ServiceException() {
    }

    public ServiceException(String message) {
        this.message = message;
    }

    public ServiceException(ResponseEnum responseEnum) {
        this.message = responseEnum.getMessage();
        this.code = responseEnum.getCode();
    }

    /**
     * 服务异常
     *
     * @param responseEnum
     * @param msg
     */
    public ServiceException(ResponseEnum responseEnum, String... msg) {
        this.message = String.format(responseEnum.getMessage(), msg);
        this.code = responseEnum.getCode();
    }


    public ServiceException(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    public ServiceException setMessage(String message) {
        this.message = message;
        return this;
    }

    public ServiceException setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
        return this;
    }
}