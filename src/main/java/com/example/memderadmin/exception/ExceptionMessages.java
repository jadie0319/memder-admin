package com.example.memderadmin.exception;

public class ExceptionMessages {
    public static final String INVALID_PASSWORD = "유효하지 않은 비밀번호 입니다.";
    public static final String INVALID_GENDER = "유효하지 않은 성별 입니다. code: %s";
    public static final String NOT_FOUND_MEMBER_LOGIN_ID = "회원을 찾을 수 없습니다. loginId: %s";
    public static final String NOT_FOUND_MEMBER_ID = "회원을 찾을 수 없습니다. id: %d";
    public static final String NOT_MATCH_PASSWORD = "비밀먼호가 일치하지 않습니다.";
    public static final String END_TOKEN_EXPIRATION = "토큰 유효기간이 지났습니다.";
    public static final String NOT_MATCH_LOGIN_ID = "토큰의 로그인 아이디가 다릅니다. %s";
    public static final String EMPTY_TOKEN = "토큰이 없습니다.";
}
