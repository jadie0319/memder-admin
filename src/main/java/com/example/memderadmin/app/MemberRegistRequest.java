package com.example.memderadmin.app;

import lombok.Builder;

import java.time.LocalDate;

public record MemberRegistRequest(
        String name,
        LocalDate birthDate,
        String gender,
        String loginId,
        String password,
        String group,
        String limitIngredients,
        String description
) {

    @Builder
    public MemberRegistRequest {}



    public static MemberRegistRequest host(String name, LocalDate birthDate, String gender, String id, String password, String group) {
        return MemberRegistRequest.builder()
                .name(name)
                .birthDate(birthDate)
                .gender(gender)
                .loginId(id)
                .password(password)
                .group(group)
                .build();
    }

    public static MemberRegistRequest participant(String name, LocalDate birthDate, String gender, String id,
                                                  String password, String limitIngredients, String description) {
        return MemberRegistRequest.builder()
                .name(name)
                .birthDate(birthDate)
                .gender(gender)
                .loginId(id)
                .password(password)
                .limitIngredients(limitIngredients)
                .description(description)
                .build();
    }


}
