package com.podo.climb.repository;

import com.podo.climb.entity.MemberFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberFavoriteRepository extends JpaRepository<MemberFavorite, Long> {
    void deleteByMemberIdAndAndGymId(Long memberId, Long gymId);

    List<MemberFavorite> findByMemberId(Long memberId);

    MemberFavorite findByMemberIdAndGymId(Long memberId, Long gymId);
}
