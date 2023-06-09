package com.example.memderadmin.app;

import com.example.memderadmin.domain.Role;
import lombok.Builder;

public record MemberRoleUpdateRequest(
        Role role,
        String group,
        String limitIngredients,
        String description
) {
    @Builder
    public MemberRoleUpdateRequest {
    }

    public static MemberRoleUpdateRequest toHost(String group) {
        return MemberRoleUpdateRequest.builder()
                .role(Role.HOST)
                .group(group)
                .build();
    }

    public static MemberRoleUpdateRequest toParticipant(String limitIngredients, String description) {
        return MemberRoleUpdateRequest.builder()
                .role(Role.PARTICIPANT)
                .limitIngredients(limitIngredients)
                .description(description)
                .build();
    }

}
