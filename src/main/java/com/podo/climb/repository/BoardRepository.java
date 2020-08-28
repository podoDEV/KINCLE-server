package com.podo.climb.repository;

import com.podo.climb.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface BoardRepository extends PagingAndSortingRepository<Board, Long> {
    Board findByBoardId(Long boardId);

    Page<Board> findAllByGymId(Long gymId, Pageable pageable);

    Page<Board> findAllByGymIdAndCreatorId(Long gymId, Long creatorId, Pageable pageable);

    @Query(value = "SELECT b FROM Board b INNER JOIN MembersBoard mb ON b.boardId = mb.boardId WHERE b.gymId = ?1 AND mb.memberId = ?2 AND mb.likeFlag = true",
            countQuery = "SELECT COUNT(b) FROM Board b INNER JOIN MembersBoard mb ON b.boardId = mb.boardId WHERE  b.gymId = ?1 AND mb.memberId = ?2 AND mb.likeFlag = true")
    Page<Board> findLikeByGymId(Long gymId, Long memberId, Pageable pageable);

    @Query(value = "SELECT b FROM Board b INNER JOIN MembersBoard mb ON b.boardId = mb.boardId WHERE b.gymId = ?1 AND mb.memberId = ?2 AND mb.solveFlag = true",
            countQuery = "SELECT COUNT(b) FROM Board b INNER JOIN MembersBoard mb ON b.boardId = mb.boardId WHERE  b.gymId = ?1 AND mb.memberId = ?2 AND mb.solveFlag = true")
    Page<Board> findSolveByGymId(Long gymId, Long memberId, Pageable pageable);

    @Query(value = "SELECT b FROM Board b INNER JOIN MembersBoard mb ON b.boardId = mb.boardId WHERE b.gymId = ?1 AND mb.memberId = ?2 AND mb.followFlag = true",
            countQuery = "SELECT COUNT(b) FROM Board b INNER JOIN MembersBoard mb ON b.boardId = mb.boardId WHERE  b.gymId = ?1 AND mb.memberId = ?2 AND mb.followFlag = true")
    Page<Board> findFollowByGymId(Long gymId, Long memberId, Pageable pageable);

    Page<Board> findAllByCreatorId(Long memberId, Pageable pageable);

    @Query(value = "SELECT b FROM Board b INNER JOIN MembersBoard mb ON b.boardId = mb.boardId WHERE mb.memberId = ?1 AND mb.likeFlag = true",
            countQuery = "SELECT COUNT(b) FROM Board b INNER JOIN MembersBoard mb ON b.boardId = mb.boardId WHERE mb.memberId = ?1  AND mb.likeFlag = true")
    Page<Board> findLike(Long memberId, Pageable pageable);

    @Query(value = "SELECT b FROM Board b INNER JOIN MembersBoard mb ON b.boardId = mb.boardId WHERE mb.memberId = ?1 AND mb.solveFlag = true",
            countQuery = "SELECT COUNT(b) FROM Board b INNER JOIN MembersBoard mb ON b.boardId = mb.boardId WHERE mb.memberId = ?1  AND mb.solveFlag = true")
    Page<Board> findSolve(Long memberId, Pageable pageable);

    @Query(value = "SELECT b FROM Board b INNER JOIN MembersBoard mb ON b.boardId = mb.boardId WHERE mb.memberId = ?1 AND mb.followFlag = true",
            countQuery = "SELECT COUNT(b) FROM Board b INNER JOIN MembersBoard mb ON b.boardId = mb.boardId WHERE mb.memberId = ?1  AND mb.followFlag = true")
    Page<Board> findFollow(Long memberId, Pageable pageable);

    void deleteByBoardId(Long boardId);

    @Modifying
    @Query("UPDATE Board b SET b.likeCount = b.likeCount + 1 WHERE b.boardId = ?1")
    void increaseLikeCount(Long boardId);

    @Modifying
    @Query("UPDATE Board b SET b.likeCount = b.likeCount - 1 WHERE b.boardId = ?1")
    void decreaseLikeCount(Long boardId);

    @Modifying
    @Query("UPDATE Board b SET b.followCount = b.followCount + 1 WHERE b.boardId = ?1")
    void increaseFollowCount(Long boardId);

    @Modifying
    @Query("UPDATE Board b SET b.followCount = b.followCount - 1 WHERE b.boardId = ?1")
    void decreaseFollowCount(Long boardId);

}
