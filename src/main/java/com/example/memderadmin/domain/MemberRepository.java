package com.example.memderadmin.domain;


import org.springframework.data.repository.Repository;

public interface MemberRepository extends Repository<Member,Long> {
    Member save(Member member);
}
