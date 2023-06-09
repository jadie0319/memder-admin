package com.example.memderadmin.exception;

public class ExceptionMessages {
    public static final String INVALID_PASSWORD = "유효하지 않은 비밀번호 입니다.";
    public static final String INVALID_GENDER = "유효하지 않은 성별 입니다. code: %s";
    public static final String NOT_FOUND_MEMBER_LOGIN_ID = "회원을 찾을 수 없습니다. loginId: %s";
    public static final String NOT_FOUND_MEMBER_ID = "회원을 찾을 수 없습니다. id: %d";
    public static final String NOT_MATCH_PASSWORD = "비밀먼호가 일치하지 않습니다.";
    public static final String END_TOKEN_EXPIRATION = "토큰 유효기간이 지났습니다.";
    public static final String NOT_MATCH_LOGIN_ID = "토큰의 로그인 아이디와 다릅니다. 요청 loginId=%s";
    public static final String EMPTY_TOKEN = "토큰이 없습니다.";
    public static final String EMPTY_ROLE = "변경해야할 역할이 NULL 입니다.";
    public static final String EMPTY_GROUP = "변경해야할 소속이 NULL 입니다.";
    public static final String EMPTY_LIMIT_INGREDIENTS = "변경해야할 취식제한재료가 NULL 입니다.";
    public static final String EMPTY_DESCRIPTION = "변경해야할 자기소개가 NULL 입니다.";
    public static final String NOT_EMPTY_GROUP = "참여자로 변경하려면 소속이 NULL 이어야 합니다.";
}
