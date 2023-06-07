package com.example.memderadmin.domain;

import com.example.memderadmin.app.MemberRegisterRequest;
import com.example.memderadmin.exception.ExceptionMessages;
import com.example.memderadmin.exception.NotAuthorizedMemberException;
import com.example.memderadmin.exception.NotMatchPasswordException;
import com.example.memderadmin.infra.GenderConverter;
import com.example.memderadmin.infra.RoleConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.example.memderadmin.exception.ExceptionMessages.NOT_AUTHORIZE_MEMBER;

@Getter
@Builder
@Entity
@Table(name = "members", schema = "moim")
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Convert(converter = RoleConverter.class)
    private Role role;
    private LocalDate birthDate;
    @Convert(converter = GenderConverter.class)
    private Gender gender;
    private String loginId;
    private String password;
    private String email;
    @Column(name = "groups")
    private String group;
    private String limitIngredients;
    private String description;
    private LocalDateTime regDt;

    protected Member() {
    }

    public static Member of(MemberRegisterRequest request) {
        if (request.isHost()) {
            return Member.builder()
                    .name(request.name())
                    .role(request.role())
                    .birthDate(request.birthDate())
                    .gender(Gender.ofCode(request.gender()))
                    .loginId(request.loginId())
                    .password(request.password())
                    .email(request.email())
                    .group(request.group())
                    .regDt(LocalDateTime.now())
                    .build();
        }
        return Member.builder()
                .name(request.name())
                .role(request.role())
                .birthDate(request.birthDate())
                .gender(Gender.ofCode(request.gender()))
                .loginId(request.loginId())
                .password(request.password())
                .email(request.email())
                .limitIngredients(request.limitIngredients())
                .description(request.description())
                .regDt(LocalDateTime.now())
                .build();
    }

    public void checkPassword(String inputPassword) {
        if (!ObjectUtils.nullSafeEquals(this.password, inputPassword)) {
            throw new NotMatchPasswordException(ExceptionMessages.NOT_MATCH_PASSWORD);
        }
    }

    public void update(MemberUpdateDto dto) {
        this.name = dto.name();
        this.birthDate = dto.birthDate();
        this.gender = Gender.ofCode(dto.gender());
        this.password = dto.password();
        this.email = dto.email();
    }

    public void isMatchLoginMember(String loginId) {
        if (!this.loginId.equals(loginId)) {
            throw new NotAuthorizedMemberException(NOT_AUTHORIZE_MEMBER.formatted(loginId));
        }
    }
}
