package com.example.memderadmin.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FakeMemberRepository implements MemberRepository {
    private Map<Long, Member> map = new HashMap<>();

    @Override
    public Member save(Member member) {
        map.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findByLoginId(String loginId) {
        return map.values()
                .stream()
                .filter(member -> member.getLoginId().equals(loginId))
                .findFirst();
    }
}
