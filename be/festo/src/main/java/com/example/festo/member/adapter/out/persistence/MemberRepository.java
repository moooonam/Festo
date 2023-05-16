package com.example.festo.member.adapter.out.persistence;

import com.example.festo.member.domain.AuthId;
import com.example.festo.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByAuthId(AuthId authId);
}
