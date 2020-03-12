package com.podo.climb.controller;

import com.podo.climb.model.request.CreateBoardRequest;
import com.podo.climb.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class BoardController {

    private BoardService boardService;

    @Autowired
    BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/v1/board")
    public String createBoard(
            @RequestBody CreateBoardRequest createBoardRequest, HttpServletRequest request) {
        boardService.createBoard(createBoardRequest);
        return "success";
    }

    @GetMapping("/v1/board/{boardId}")
    public String readBoard(@PathVariable Long boardId) {
        return boardService.readBoard(boardId).toString();
    }

    @DeleteMapping("/v1/board")
    public String deleteBoard() {
        return null;
    }



}
