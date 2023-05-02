package com.paymong.auth.global.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorStateCode {

    NOTFOUNDUSER("404", "사용자를 찾을 수 없습니다"),
    UNAUTHORIXED("403", "권한이 없습니다"),
    REDIS("408", "redis 연결 오류"),
    RUNTIME("500", "서버 에러");
    private final String code;
    private final String message;


}
