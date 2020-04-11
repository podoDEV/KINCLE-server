package com.podo.climb.service;

import com.podo.climb.Utils.IdGenerator;
import com.podo.climb.entity.Board;
import com.podo.climb.entity.Member;
import com.podo.climb.model.request.CreateBoardRequest;
import com.podo.climb.model.response.BoardResponse;
import com.podo.climb.repository.BoardRepository;
import com.podo.climb.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardService {

    private MemberService memberService;
    private BoardRepository boardRepository;
    private MemberRepository memberRepository;

    @Autowired
    BoardService(MemberService memberService,
                 BoardRepository boardRepository,
                 MemberRepository memberRepository) {
        this.memberService = memberService;
        this.boardRepository = boardRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Board createBoard(CreateBoardRequest requestedCreateBoard) {
        Member member = memberService.getCurrentMember();
        Board board = Board.builder()
                           .boardId(IdGenerator.generate())
                           .title(requestedCreateBoard.getTitle())
                           .description(requestedCreateBoard.getDescription())
                           .imageUrl(requestedCreateBoard.getImageUrl()).build();
        board.setCreator(member.getNickname());
        boardRepository.save(board);
        return board;
    }


    @Transactional
    public BoardResponse getBoard(Long boardId) {
        Board board = boardRepository.findByBoardId(boardId);
        return board.toBoardResponse();
    }

    @Transactional
    public void deleteBoard() {

    }

}
