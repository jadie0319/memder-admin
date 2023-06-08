package com.example.memderadmin.domain;

import com.example.memderadmin.exception.ExceptionMessages;
import com.example.memderadmin.exception.MemberNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FakeMemberRepository implements MemberRepository {
    private Map<Long, Member> map = new HashMap<>();
    private Long id = 1L;

    @Override
    public Member save(Member member) {
        member.setId(id);
        map.put(id++, member);
        return member;
    }

    @Override
    public Optional<Member> findByLoginId(String loginId) {
        return map.values()
                .stream()
                .filter(member -> member.getLoginId().equals(loginId))
                .findFirst();
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(map.get(id));
    }

    public void clear() {
        map.clear();
        id = 1L;
    }
}
