package com.podo.climb.repository;

import com.podo.climb.entity.MembersGym;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembersGymRepository extends JpaRepository<MembersGym, Long> {
    void deleteByMemberIdAndAndGymId(Long memberId, Long gymId);

    List<MembersGym> findByMemberId(Long memberId);

    MembersGym findByMemberIdAndGymId(Long memberId, Long gymId);
}
