package com.example.memderadmin.app;

import com.example.memderadmin.domain.Role;
import lombok.Builder;

public record MemberRoleChangeRequest(
        Role role,
        String group,
        String limitIngredients,
        String description
) {
    @Builder
    public MemberRoleChangeRequest {
    }

    public static MemberRoleChangeRequest toHost(String group) {
        return MemberRoleChangeRequest.builder()
                .role(Role.HOST)
                .group(group)
                .build();
    }
}
