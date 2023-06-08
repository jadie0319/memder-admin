package com.example.memderadmin.app;

import com.example.memderadmin.domain.Gender;
import com.example.memderadmin.domain.Member;
import lombok.Builder;

import java.time.LocalDate;

public record MemberQueryResponse(
        Long id,
        String name,
        LocalDate birthDate,
        Gender gender,
        String loginId,
        String email,
        String group,
        String limitIngredients,
        String description
) {
    @Builder
    public MemberQueryResponse {
    }

    public static MemberQueryResponse of(Member member) {
        return MemberQueryResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .birthDate(member.getBirthDate())
                .gender(member.getGender())
                .loginId(member.getLoginId())
                .email(member.getEmail())
                .group(member.getGroup())
                .limitIngredients(member.getLimitIngredients())
                .description(member.getDescription())
                .build();
    }
}
