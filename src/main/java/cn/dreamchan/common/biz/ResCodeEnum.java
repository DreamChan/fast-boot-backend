package cn.dreamchan.common.biz;


/**
 * 自定义错误类型
 *
 * @author DreamChan
 */
public enum ResCodeEnum {

    SUCCESS(200, "成功"),
    FAILURE(400, "失败"),
    UNAUTHORIZED(401, "未认证"),
    FORBIDDEN(403, "未授权"),
    NOT_FOUND(404, "不存在"),
    INTERNAL_SERVER_ERROR(500, "内部错误");

    private final int code;

    private final String msg;

    ResCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
