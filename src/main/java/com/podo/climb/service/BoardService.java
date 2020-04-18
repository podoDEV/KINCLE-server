package com.podo.climb.service;

import com.podo.climb.Utils.IdGenerator;
import com.podo.climb.entity.Board;
import com.podo.climb.entity.Comment;
import com.podo.climb.entity.Member;
import com.podo.climb.model.request.CreateBoardRequest;
import com.podo.climb.model.response.BoardResponse;
import com.podo.climb.model.response.CommentResponse;
import com.podo.climb.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService {

    private MemberService memberService;
    private CommentService commentService;
    private BoardRepository boardRepository;

    @Autowired
    BoardService(MemberService memberService,
                 CommentService commentService,
                 BoardRepository boardRepository) {
        this.memberService = memberService;
        this.commentService = commentService;
        this.boardRepository = boardRepository;
    }

    @Transactional
    public Board createBoard(CreateBoardRequest requestedCreateBoard) {
        Member member = memberService.getCurrentMember();
        Board board = Board.builder()
                           .boardId(IdGenerator.generate())
                           .title(requestedCreateBoard.getTitle())
                           .description(requestedCreateBoard.getDescription())
                           .imageUrl(requestedCreateBoard.getImageUrl())
                           .createAt(Calendar.getInstance())
                           .creator(member.getNickname())
                           .build();
        boardRepository.save(board);
        return board;
    }


    @Transactional
    public BoardResponse getBoard(Long boardId) {
        Board board = boardRepository.findByBoardId(boardId);
        return board.toBoardResponse();
    }


    public List<CommentResponse> getComments(Long boardId) {
        List<Comment> comments = commentService.getComments(boardId);
        return comments.stream().map(Comment::toCommentResponse).collect(Collectors.toList());
    }

    @Transactional
    public void deleteBoard() {

    }

}
