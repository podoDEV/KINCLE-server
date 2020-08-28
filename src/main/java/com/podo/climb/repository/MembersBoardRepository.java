package com.podo.climb.repository;

import com.podo.climb.entity.MembersBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MembersBoardRepository extends JpaRepository<MembersBoard, Long> {
    MembersBoard findFirstByMemberIdAndBoardId(Long memberId, Long boardId);

    @Query("SELECT AVG(NULLIF(mb.score,0)) FROM MembersBoard mb WHERE mb.boardId = ?1 and mb.score IS NOT NULL")
    Float findAverageScoreByBoardId(Long boardId);

    void deleteAllByBoardId(Long boardId);
}
