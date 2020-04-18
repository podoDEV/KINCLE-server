package com.podo.climb.controller;

import com.podo.climb.entity.Comment;
import com.podo.climb.model.request.CommentRequest;
import com.podo.climb.model.response.ApiResult;
import com.podo.climb.model.response.CommentResponse;
import com.podo.climb.model.response.SuccessfulResult;
import com.podo.climb.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {

    private CommentService commentService;

    @Autowired
    CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/v1/comment")
    public ApiResult<Comment> createComment(@RequestBody CommentRequest commentRequest) {
        return new SuccessfulResult<>(commentService.createComment(commentRequest));
    }

    @GetMapping("/v1/comment/{commentId}")
    public ApiResult<CommentResponse> getBoard(@PathVariable Long commentId) {
        return new SuccessfulResult<>(commentService.getComment(commentId));
    }


}
