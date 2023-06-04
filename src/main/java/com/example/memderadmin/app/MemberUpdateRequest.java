package com.example.memderadmin.app;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

public record MemberUpdateRequest(
        Long id,
        String name,
        LocalDate birthDate,
        String gender,
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$", message = "비밀번호는 영문자, 숫자, 특수문자가 하나 이상 포함된 8자리 문자열 이어야 합니다.")
        String password,
        @Email(message = "이메일은 필수값입니다.") String email
) {

    public static MemberUpdateRequest of(String name, LocalDate birthDate, String gender, String password, String email) {
        return new MemberUpdateRequest(null, name, birthDate, gender, password, email);
    }
}
