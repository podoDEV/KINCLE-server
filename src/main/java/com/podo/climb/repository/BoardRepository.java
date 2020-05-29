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

    void deleteByBoardId(Long boardId);

    @Modifying
    @Query("UPDATE Board b set b.likeCount = b.likeCount + 1 WHERE b.boardId = ?1")
    void increaseCount(Long boardId);

    @Modifying
    @Query("UPDATE Board b set b.likeCount = b.likeCount - 1 WHERE b.boardId = ?1")
    void decreaseCount(Long boardId);

}
