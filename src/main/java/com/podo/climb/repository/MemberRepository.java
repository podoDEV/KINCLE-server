package com.podo.climb.repository;

import com.podo.climb.entity.Member;
import com.podo.climb.model.OauthType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmailAddress(String emailAddress);

    Member findByTokenAndOauthType(String token, OauthType oauthType);

    Member findByMemberId(Long memberId);

    Member saveAndFlush(Member member);
}
