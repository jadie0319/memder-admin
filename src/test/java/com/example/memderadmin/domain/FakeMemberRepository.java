package com.example.memderadmin.domain;

import java.util.HashMap;
import java.util.Map;

public class FakeMemberRepository implements MemberRepository {
    private Map<Long, Member> map = new HashMap<>();

    @Override
    public Member save(Member member) {
        map.put(member.getId(), member);
        return member;
    }
}
