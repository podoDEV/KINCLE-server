package com.podo.climb.repository;

import com.podo.climb.entity.MembersBoardLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembersBoardLikeRepository extends JpaRepository<MembersBoardLike, Long> {
    MembersBoardLike findFirstByMemberIdAndBoardId(Long memberId, Long boardId);

    void deleteAllByMemberIdAndBoardId(Long memberId, Long boardId);
}
