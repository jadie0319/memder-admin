package com.example.memderadmin.domain;

import com.example.memderadmin.app.MemberRoleUpdateRequest;
import lombok.Builder;

public record MemberRoleUpdateDto(
        Role role,
        String group,
        String limitIngredients,
        String description
) {
    @Builder
    public MemberRoleUpdateDto {}
    public static MemberRoleUpdateDto of(MemberRoleUpdateRequest request) {
        return MemberRoleUpdateDto.builder()
                .role(request.role())
                .group(request.group())
                .limitIngredients(request.limitIngredients())
                .description(request.description())
                .build();
    }
}
