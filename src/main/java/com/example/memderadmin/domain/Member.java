package com.example.memderadmin.domain;

import com.example.memderadmin.app.MemberRegisterRequest;
import com.example.memderadmin.exception.AuthenticationMemberException;
import com.example.memderadmin.exception.ExceptionMessages;
import com.example.memderadmin.exception.NotAuthorizedMemberException;
import com.example.memderadmin.infra.GenderConverter;
import com.example.memderadmin.infra.RoleConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.example.memderadmin.exception.ExceptionMessages.NOT_MATCH_LOGIN_ID;

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
            throw new AuthenticationMemberException(ExceptionMessages.NOT_MATCH_PASSWORD);
        }
    }

    public void update(MemberUpdateDto dto) {
        if (!ObjectUtils.isEmpty(dto.name()) && !ObjectUtils.nullSafeEquals(this.name, dto.name())) {
            this.name = dto.name();
        }
        if (!ObjectUtils.isEmpty(dto.birthDate()) && !ObjectUtils.nullSafeEquals(this.birthDate, dto.birthDate())) {
            this.birthDate = dto.birthDate();
        }
        if (!ObjectUtils.isEmpty(dto.gender()) && !ObjectUtils.nullSafeEquals(this.gender, dto.gender())) {
            this.gender = Gender.ofCode(dto.gender());
        }
        if (!ObjectUtils.isEmpty(dto.password()) && !ObjectUtils.nullSafeEquals(this.password, dto.password())) {
            this.password = dto.password();
        }
        if (!ObjectUtils.isEmpty(dto.email()) && !ObjectUtils.nullSafeEquals(this.email, dto.email())) {
            this.email = dto.email();
        }
    }

    public void isMatchLoginMember(String loginId) {
        if (!this.loginId.equals(loginId)) {
            throw new NotAuthorizedMemberException(NOT_MATCH_LOGIN_ID.formatted(loginId));
        }
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void updateRole(MemberRoleUpdateDto dto) {
        if (Role.PARTICIPANT.equals(this.role) && Role.HOST.equals(dto.role())) {
            // 현재는 참여자인데 주최자로 변경할 경우
            this.role = dto.role();
            this.group = dto.group();
        } else if (Role.HOST.equals(this.role)) {
            // 현재는 주최자인데 참여자로 변경할 경우
            this.role = dto.role();
            this.group = null;
            this.limitIngredients = dto.limitIngredients();
            this.description = dto.description();
        }

    }
}
