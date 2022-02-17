package com.fattyCorps.obj.msg;

/**
 * 服务端通用返回
 * @param <T>
 */
public class SRet<T> {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 消息内容
     */
    private String msg;
    /**
     * 消息数据
     */
    private T data;

    /**
     * 默认构造
     *
     * @param code
     * @param msg
     * @param data
     */
    public SRet(Integer code, String msg, T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> SRet<T> success(T data){
        return new SRet(HttpStatus.SUCCESS.getCode(), HttpStatus.SUCCESS.getMsg(), data);
    }

    public static SRet success(){
        return new SRet(HttpStatus.SUCCESS.getCode(), HttpStatus.SUCCESS.getMsg(), null);
    }

    public static <T> SRet<T> error(T data){
        return new SRet(HttpStatus.ERROR.getCode(), HttpStatus.ERROR.getMsg(), data);
    }

    public static SRet error(){
        return new SRet(HttpStatus.ERROR.getCode(), HttpStatus.ERROR.getMsg(), null);
    }

    public static SRet error(Integer code, String msg){
        return new SRet(code, msg, null);
    }
}