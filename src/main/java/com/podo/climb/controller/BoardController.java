package com.podo.climb.controller;

import com.podo.climb.entity.Board;
import com.podo.climb.model.request.CreateBoardRequest;
import com.podo.climb.model.response.ApiResult;
import com.podo.climb.model.response.BoardResponse;
import com.podo.climb.model.response.CommentResponse;
import com.podo.climb.model.response.SuccessfulResult;
import com.podo.climb.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BoardController {

    private BoardService boardService;

    @Autowired
    BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/v1/board")
    public ApiResult<Board> createBoard(
            @RequestBody CreateBoardRequest createBoardRequest) {
        return new SuccessfulResult<>(boardService.createBoard(createBoardRequest));
    }

    @GetMapping("/v1/board/{boardId}")
    public ApiResult<BoardResponse> getBoard(@PathVariable Long boardId) {
        return new SuccessfulResult<>(boardService.getBoard(boardId));
    }

    @GetMapping("/v1/board/{boardId}/comments")
    public ApiResult<List<CommentResponse>> getComments(@PathVariable Long boardId) {
        return new SuccessfulResult<>(boardService.getComments(boardId));
    }

//    @DeleteMapping("/v1/board")
//    public ApiResult deleteBoard() {
//        return new SuccessfulResult();
//    }


}
