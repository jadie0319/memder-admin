package com.example.memderadmin.app;

import com.example.memderadmin.domain.Role;
import lombok.Builder;

import java.time.LocalDate;

public record MemberRegistRequest(
        String name,
        Role role,
        LocalDate birthDate,
        String gender,
        String loginId,
        String password,
        String email,
        String group,
        String limitIngredients,
        String description
) {

    @Builder
    public MemberRegistRequest {}



    public static MemberRegistRequest host(String name, LocalDate birthDate, String gender, String id, String password, String email, String group) {
        return MemberRegistRequest.builder()
                .name(name)
                .role(Role.HOST)
                .birthDate(birthDate)
                .gender(gender)
                .loginId(id)
                .password(password)
                .email(email)
                .group(group)
                .build();
    }

    public static MemberRegistRequest participant(String name, LocalDate birthDate, String gender, String id,
                                                  String password, String email, String limitIngredients, String description) {
        return MemberRegistRequest.builder()
                .name(name)
                .role(Role.PARTICIPANT)
                .birthDate(birthDate)
                .gender(gender)
                .loginId(id)
                .password(password)
                .email(email)
                .limitIngredients(limitIngredients)
                .description(description)
                .build();
    }


    public boolean isHost() {
        return Role.HOST.equals(role);
    }
}
