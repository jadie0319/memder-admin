package com.example.memderadmin.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {

    // 영문자, 숫자, 특수문자가 하나이상 포함된 8자리 문자열 이어야 합니다.
    private static final Pattern pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$");

    public static boolean validate(String password) {
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

}
