package com.podo.climb.repository;

import com.podo.climb.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findByCommentId(Long commentId);

    List<Comment> findByBoardId(Long boardId);
}
