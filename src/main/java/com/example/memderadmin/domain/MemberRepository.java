package com.example.memderadmin.domain;


import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface MemberRepository extends Repository<Member,Long> {
    Member save(Member member);
    Optional<Member> findByLoginId(String loginId);
}
