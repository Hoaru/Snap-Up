package com.pro.snap.vo;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.Getter;

@Getter
@ToString
@AllArgsConstructor
public enum RespBeanEnum {
    // 通用
    SUCCESS(200, "SUCCESS"),
    ERROR(500, "SERVER EXCEPTION"),
    // 登录5002
    LOGIN_ERROR(500210, "WRONG ACCOUNT/PASSWORD"),
    MOBILE_ERROR(500211, "WRONG PHONE NUMBERS FORMAT"),
    NOTFOUND_ERROR(500209, "USER NOT FOUND"),
    BIND_ERROR(500212, "ARGUMENT INVALID EXCEPTION"),
    //秒杀5005
    EMPTY_STOCK(500500, "OUT OF STOCK"),
    REPEAT_ERROR(500501, "LIMITED TO 1 PER CUSTOMER")
    ;

    private final Integer code;
    private final String message;
}
