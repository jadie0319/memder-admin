package com.example.memderadmin.app;

import com.example.memderadmin.domain.Role;
import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

public record MemberRegisterRequest(
        @NotBlank(message = "이름은 필수값입니다.") String name,
        @NotNull(message = "역할은 필수값입니다.") Role role,
        LocalDate birthDate,
        @NotBlank(message = "성별은 필수값입니다.") String gender,
        @NotBlank(message = "로그인ID는 필수값입니다.") String loginId,
        @NotBlank(message = "비밀번호는 필수값입니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$", message = "비밀번호는 영문자, 숫자, 특수문자가 하나 이상 포함된 8자리 문자열 이어야 합니다.")
        String password,
        @Email(message = "이메일은 필수값입니다.") String email,
        String group,
        String limitIngredients,
        String description
) {

    @Builder
    public MemberRegisterRequest {
    }


    public static MemberRegisterRequest host(String name, LocalDate birthDate, String gender, String id, String password, String email, String group) {
        return MemberRegisterRequest.builder()
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

    public static MemberRegisterRequest participant(String name, LocalDate birthDate, String gender, String id,
                                                    String password, String email, String limitIngredients, String description) {
        return MemberRegisterRequest.builder()
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
