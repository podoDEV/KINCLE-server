package com.podo.climb.repository;

import com.podo.climb.entity.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRoleRepository extends JpaRepository<MemberRole, String> {

    MemberRole saveAndFlush(MemberRole member);

}
