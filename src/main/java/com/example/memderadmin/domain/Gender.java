package com.example.memderadmin.domain;

import java.util.Arrays;

import static com.example.memderadmin.exception.ExceptionMessages.INVALID_GENDER;

public enum Gender {
    FEMALE("F"),
    MALE("M")
    ;

    private String code;

    Gender(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Gender ofCode(String code) {
        return Arrays.stream(values())
                .filter( gender -> gender.code.equals(code))
                .findFirst()
                .orElseThrow( () -> new IllegalArgumentException(INVALID_GENDER.formatted(code)));
    }
}
