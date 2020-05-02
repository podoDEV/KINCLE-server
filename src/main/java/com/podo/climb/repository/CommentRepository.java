package com.podo.climb.repository;

import com.podo.climb.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findByCommentId(Long commentId);

    Page<Comment> findAllByBoardId(Long boardId, Pageable pageable);

}
