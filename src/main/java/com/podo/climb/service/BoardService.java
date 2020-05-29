package com.podo.climb.service;

import com.podo.climb.Utils.IdGenerator;
import com.podo.climb.entity.Board;
import com.podo.climb.entity.Comment;
import com.podo.climb.entity.Member;
import com.podo.climb.entity.MembersBoardLike;
import com.podo.climb.model.request.CreateBoardRequest;
import com.podo.climb.model.response.BoardResponse;
import com.podo.climb.model.response.CommentResponse;
import com.podo.climb.repository.BoardRepository;
import com.podo.climb.repository.MembersBoardLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

@Service
public class BoardService {

    private MemberService memberService;
    private CommentService commentService;
    private BoardRepository boardRepository;
    private MembersBoardLikeRepository membersBoardLikeRepository;

    @Autowired
    BoardService(MemberService memberService,
                 CommentService commentService,
                 BoardRepository boardRepository,
                 MembersBoardLikeRepository membersBoardLikeRepository) {
        this.memberService = memberService;
        this.commentService = commentService;
        this.boardRepository = boardRepository;
        this.membersBoardLikeRepository = membersBoardLikeRepository;
    }

    @Transactional
    public Board createBoard(CreateBoardRequest requestedCreateBoard) {
        Member member = memberService.getCurrentMember();
        Board board = Board.builder()
                           .boardId(IdGenerator.generate())
                           .title(requestedCreateBoard.getTitle())
                           .description(requestedCreateBoard.getDescription())
                           .imageUrl(requestedCreateBoard.getImageUrl())
                           .createdAt(Calendar.getInstance())
                           .creator(member.getNickname())
                           .gymId(requestedCreateBoard.getGymId())
                           .likeCount(0)
                           .build();
        boardRepository.save(board);
        return board;
    }


    @Transactional
    public BoardResponse getBoard(Long boardId) {
        Board board = boardRepository.findByBoardId(boardId);
        if (board == null) {
            return new BoardResponse();
        }


        BoardResponse boardResponse = board.toBoardResponse();
        Member member = memberService.getCurrentMember();
        boardResponse.setIsLike(membersBoardLikeRepository.findFirstByMemberIdAndBoardId(member.getMemberId(), boardResponse.getBoardId()) != null);
        return boardResponse;
    }

    @Transactional
    public Page<BoardResponse> getBoards(Long gymId, Pageable pageable) {
        Page<Board> boards;
        if (gymId != null) {
            boards = boardRepository.findAllByGymId(gymId, pageable);
        } else {
            boards = boardRepository.findAll(pageable);
        }
        Member member = memberService.getCurrentMember();
        return boards.map(Board::toBoardResponse)
                     .map(boardResponse -> {
                         boardResponse.setIsLike(membersBoardLikeRepository.findFirstByMemberIdAndBoardId(member.getMemberId(), boardResponse.getBoardId()) != null);
                         return boardResponse;
                     });
    }


    @Transactional
    public Page<CommentResponse> getComments(Long boardId, Pageable pageable) {
        Page<Comment> comments = commentService.getComments(boardId, pageable);
        return comments.map(Comment::toCommentResponse);

    }

    @Transactional
    public void createLike(Long boardId) {
        Member member = memberService.getCurrentMember();
        if ((membersBoardLikeRepository.findFirstByMemberIdAndBoardId(member.getMemberId(), boardId) != null)) {
            return;
        }
        MembersBoardLike membersBoardLike = MembersBoardLike.builder()
                                                            .memberFavoriteId(IdGenerator.generate())
                                                            .memberId(member.getMemberId())
                                                            .boardId(boardId)
                                                            .build();
        membersBoardLikeRepository.save(membersBoardLike);
        boardRepository.increaseCount(boardId);
    }

    @Transactional
    public void deleteLike(Long boardId) {
        Member member = memberService.getCurrentMember();
        membersBoardLikeRepository.deleteAllByMemberIdAndBoardId(member.getMemberId(), boardId);
        boardRepository.decreaseCount(boardId);
    }


    @Transactional
    public void deleteBoard(Long boardId) {
        Board board = boardRepository.findByBoardId(boardId);
        if (board == null) {
            return;
        }
        membersBoardLikeRepository.deleteAllByBoardId(boardId);
        boardRepository.deleteByBoardId(boardId);
    }

}
