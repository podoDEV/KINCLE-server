package com.podo.climb.service;

import com.podo.climb.Utils.IdGenerator;
import com.podo.climb.entity.Board;
import com.podo.climb.entity.Member;
import com.podo.climb.model.request.CreateBoardRequest;
import com.podo.climb.model.response.BoardResponse;
import com.podo.climb.repository.BoardRepository;
import com.podo.climb.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardService {

    private BoardRepository boardRepository;
    private MemberRepository memberRepository;

    @Autowired
    BoardService(BoardRepository boardRepository,
                 MemberRepository memberRepository) {
        this.boardRepository = boardRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Board createBoard(CreateBoardRequest requestedCreateBoard) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Board board = new Board();
        board.setBoardId(IdGenerator.generate());
        board.setSubject(requestedCreateBoard.getTitle());
        board.setComment(requestedCreateBoard.getDescription());
        board.setImageUrl(requestedCreateBoard.getImageUrl());
        Member member = memberRepository.findByMemberId(Long.valueOf(user.getUsername()));
        board.setCreator(member.getNickname());
        boardRepository.save(board);
        return board;
    }


    @Transactional
    public BoardResponse readBoard(Long boardId) {
        Board board = boardRepository.findByBoardId(boardId);
        return board.toBoardResponse();
    }

    @Transactional
    public void deleteBoard() {

    }

}
