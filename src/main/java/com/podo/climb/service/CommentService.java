package com.podo.climb.service;

import com.podo.climb.Utils.IdGenerator;
import com.podo.climb.entity.Comment;
import com.podo.climb.entity.Member;
import com.podo.climb.model.request.CommentRequest;
import com.podo.climb.model.response.CommentResponse;
import com.podo.climb.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

@Service
public class CommentService {

    private MemberService memberService;
    private CommentRepository commentRepository;

    @Autowired
    CommentService(MemberService memberService,
                   CommentRepository commentRepository) {
        this.memberService = memberService;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public Comment createComment(CommentRequest commentRequest) {
        Member member = memberService.getCurrentMember();
        Comment comment = Comment.builder()
                                 .commentId(IdGenerator.generate())
                                 .boardId(commentRequest.getBoardId())
                                 .description(commentRequest.getDescription())
                                 .createAt(Calendar.getInstance())
                                 .creator(member.getNickname())
                                 .build();

        commentRepository.save(comment);
        return comment;
    }

    @Transactional
    public CommentResponse getComment(Long commentId) {
        Comment comment = commentRepository.findByCommentId(commentId);
        return comment.toCommentResponse();
    }

    @Transactional
    public List<Comment> getComments(Long boardId) {
        return commentRepository.findByBoardId(boardId);
    }

    @Transactional
    public void deleteBoard() {

    }

}
