package cn.qy.upload.entity.enums;

public enum AppHttpCodeEnum {


    //参考org.apache.http.HttpStatus
    // 成功 用户模块
    SUCCESS(1, 200, "ok"),
    //EDIT_SUCCESS(1, 201, "修改成功"),
    EDIT_FAIL(0, 202, "修改失败"),
    //DEL_FAIL(0, 203, "删除失败"),
    NOT_EXIST(0, 204, "指定数据不存在"),

    //4xx 客户端错误
    NEED_LOGIN(0, 400, "需要登录后操作"),
    KICT_OUT(0,400, "您已异地登录"),
    UNAUTHORIZED(0, 401, "invalid token"),
    LOGIN_FAIL(0, 402, "账号密码不匹配或账号被禁用"),
    NO_OPERATOR_AUTH(0, 403, "无权限操作"),
    CANT_DELETE_ADMIN(0, 404, "管理员角色用户不能删除"),
    REQUEST_OPERATION_FAIL(0, 406, "请求操作失败"),
    USER_EXIST(0, 406, "用户名已存在"),
    ROLE_EXIST(0, 406, "角色名已存在"),
    PASSWORD_SAME(0, 406, "新密码不能和旧密码相同"),
    OLD_PASSWORD_FAIL(0, 406, "旧密码错误"),
    DATA_ACQ_fAIL(0, 406, "数据获取失败，请重新获取数据"),
    DOAUDIT_EMPTY(0, 422, "稽核数为空"),
    PARAMETER_INVALID(0, 422, "参数不合法"),
    INVALID_FAIL(0, 422, "请求参数不合法"),

    //5XX服务端错误
    SERVER_ERROR(0, 500, "程序执行出错"),
    BAD_CREDENTIALS(0, 510, "错误的认证信息"),
    INSUFFICIENT_AUTHENTICATION(0, 511, "缺少身份验证信息"),


    ;
    int status;
    int code;
    String message;

    AppHttpCodeEnum(int status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
