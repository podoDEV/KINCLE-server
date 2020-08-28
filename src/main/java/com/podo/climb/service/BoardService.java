package com.podo.climb.service;

import com.podo.climb.Utils.IdGenerator;
import com.podo.climb.entity.Board;
import com.podo.climb.entity.Comment;
import com.podo.climb.entity.Member;
import com.podo.climb.entity.MembersBoard;
import com.podo.climb.exception.ApiFailedException;
import com.podo.climb.exception.ResourceNotFoundException;
import com.podo.climb.model.BoardFilterType;
import com.podo.climb.model.BoardType;
import com.podo.climb.model.Inner.MembersBoardFlag;
import com.podo.climb.model.request.BoardRequest;
import com.podo.climb.model.response.BoardResponse;
import com.podo.climb.model.response.CommentResponse;
import com.podo.climb.repository.BoardRepository;
import com.podo.climb.repository.MembersBoardRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Optional;

@Service
public class BoardService {

    private final MemberService memberService;
    private final CommentService commentService;
    private final BoardRepository boardRepository;
    private final MembersBoardRepository membersBoardRepository;

    @Autowired
    BoardService(MemberService memberService,
                 CommentService commentService,
                 BoardRepository boardRepository,
                 MembersBoardRepository membersBoardRepository) {
        this.memberService = memberService;
        this.commentService = commentService;
        this.boardRepository = boardRepository;
        this.membersBoardRepository = membersBoardRepository;
    }

    @Transactional
    public Board createBoard(BoardRequest requestedCreateBoard) {
        Member member = memberService.getCurrentMember();
        Board board = Board.builder()
                           .boardId(IdGenerator.generate())
                           .title(requestedCreateBoard.getTitle())
                           .description(requestedCreateBoard.getDescription())
                           .imageUrl(requestedCreateBoard.getImageUrl())
                           .createdAt(Calendar.getInstance())
                           .creator(member.getNickname())
                           .creatorId(member.getMemberId())
                           .gymId(requestedCreateBoard.getGymId())
                           .boardType(Optional.ofNullable(requestedCreateBoard.getBoardType()).orElse(BoardType.PROBLEM))
                           .level(requestedCreateBoard.getLevel())
                           .likeCount(0)
                           .followCount(0)
                           .build();
        boardRepository.save(board);
        return board;
    }


    @Transactional
    public BoardResponse getBoard(Long boardId) {
        Board board = Optional.ofNullable(boardRepository.findByBoardId(boardId)).orElseThrow(new ResourceNotFoundException());
        Member member = memberService.getCurrentMember();

        BoardResponse boardResponse = board.toBoardResponse();
        boardResponse.setFlags(membersBoardRepository.findFirstByMemberIdAndBoardId(member.getMemberId(), boardResponse.getBoardId()));
        boardResponse.setAvgScore(Optional.ofNullable(membersBoardRepository.findAverageScoreByBoardId(boardResponse.getBoardId())).orElse(0F));
        return boardResponse;
    }

    @Transactional
    public BoardResponse updateBoard(Long boardId, BoardRequest boardRequest) {
        Board board = Optional.ofNullable(boardRepository.findByBoardId(boardId)).orElseThrow(new ResourceNotFoundException());
        Member member = memberService.getCurrentMember();
        if (!member.getMemberId().equals(board.getCreatorId()))
            throw new ApiFailedException(403, "only creator can edit board");

        board.updateBoard(boardRequest);

        BoardResponse boardResponse = board.toBoardResponse();
        boardResponse.setFlags(membersBoardRepository.findFirstByMemberIdAndBoardId(member.getMemberId(), boardResponse.getBoardId()));
        return boardResponse;
    }

    @Transactional
    //TODO: file 도 제거
    public void deleteBoard(Long boardId) {
        Board board = boardRepository.findByBoardId(boardId);
        if (board == null) {
            return;
        }
        Member member = memberService.getCurrentMember();
        if (!member.getMemberId().equals(board.getCreatorId()))
            throw new ApiFailedException(403, "only creator can delete board");


        commentService.deleteComments(boardId);
        membersBoardRepository.deleteAllByBoardId(boardId);
        boardRepository.deleteByBoardId(boardId);
    }

    @Transactional
    public Page<BoardResponse> getBoards(Long gymId, BoardFilterType boardFilterType, Pageable pageable) {
        Page<Board> boards = null;
        Member member = memberService.getCurrentMember();

        if (gymId != null) {
            if (boardFilterType == null) {
                boards = boardRepository.findAllByGymId(gymId, pageable);
            } else {
                switch (boardFilterType) {
                    case REGISTER:
                        boards = boardRepository.findAllByGymIdAndCreatorId(gymId, member.getMemberId(), pageable);
                    case LIKE:
                        boards = boardRepository.findLikeByGymId(gymId, member.getMemberId(), pageable);
                        break;
                    case SOLVE:
                        boards = boardRepository.findSolveByGymId(gymId, member.getMemberId(), pageable);
                        break;
                    case FOLLOW:
                        boards = boardRepository.findFollowByGymId(gymId, member.getMemberId(), pageable);
                        break;
                }
            }
        } else {
            if (boardFilterType == null) {
                boards = boardRepository.findAll(pageable);
            } else {
                switch (boardFilterType) {
                    case REGISTER:
                        boards = boardRepository.findAllByCreatorId(member.getMemberId(), pageable);
                    case LIKE:
                        boards = boardRepository.findLike(member.getMemberId(), pageable);
                        break;
                    case SOLVE:
                        boards = boardRepository.findSolve(member.getMemberId(), pageable);
                        break;
                    case FOLLOW:
                        boards = boardRepository.findFollow(member.getMemberId(), pageable);
                        break;
                }
            }
        }
        return boards.map(Board::toBoardResponse)
                     .map(boardResponse -> {
                         boardResponse.setFlags(membersBoardRepository.findFirstByMemberIdAndBoardId(member.getMemberId(), boardResponse.getBoardId()));
                         boardResponse.setAvgScore(Optional.ofNullable(membersBoardRepository.findAverageScoreByBoardId(boardResponse.getBoardId())).orElse(0F));
                         return boardResponse;
                     });
    }


    @Transactional
    public Page<CommentResponse> getComments(Long boardId, Pageable pageable) {
        Page<Comment> comments = commentService.getComments(boardId, pageable);
        return comments.map(Comment::toCommentResponse);

    }

    @Transactional
    public void updateMembersBoard(Long boardId, MembersBoardFlag membersBoardFlag) {
        Member member = memberService.getCurrentMember();
        MembersBoard membersBoard = membersBoardRepository.findFirstByMemberIdAndBoardId(member.getMemberId(), boardId);

        if (membersBoard != null) {
            boolean isDirty = false;
            if (membersBoardFlag.getLikeFlag() != null && !membersBoardFlag.getLikeFlag().equals(membersBoard.getLikeFlag())) {
                membersBoard.setLikeFlag(membersBoardFlag.getLikeFlag());
                if (membersBoardFlag.getLikeFlag()) {
                    boardRepository.increaseLikeCount(boardId);
                } else {
                    boardRepository.decreaseLikeCount(boardId);
                }
                isDirty = true;
            }

            if (membersBoardFlag.getSolveFlag() != null && !membersBoardFlag.getSolveFlag().equals(membersBoard.getSolveFlag())) {
                membersBoard.setSolveFlag(membersBoardFlag.getSolveFlag());
                isDirty = true;
            }

            if (membersBoardFlag.getFollowFlag() != null && !membersBoardFlag.getFollowFlag().equals(membersBoard.getFollowFlag())) {
                membersBoard.setLikeFlag(membersBoardFlag.getLikeFlag());
                if (membersBoardFlag.getFollowFlag()) {
                    boardRepository.increaseFollowCount(boardId);
                } else {
                    boardRepository.decreaseFollowCount(boardId);
                }
                isDirty = true;
            }

            if (isDirty) {
                membersBoardRepository.save(membersBoard);
            }
        } else {
            Boolean likeFlag = Optional.ofNullable(membersBoardFlag.getLikeFlag()).orElse(false);
            Boolean followFlag = Optional.ofNullable(membersBoardFlag.getFollowFlag()).orElse(false);
            membersBoard = MembersBoard.builder()
                                       .memberBoardId(IdGenerator.generate())
                                       .memberId(member.getMemberId())
                                       .boardId(boardId)
                                       .likeFlag(likeFlag)
                                       .solveFlag(Optional.ofNullable(membersBoardFlag.getSolveFlag()).orElse(false))
                                       .followFlag(followFlag)
                                       .build();
            membersBoardRepository.save(membersBoard);

            if (likeFlag) {
                boardRepository.increaseLikeCount(boardId);
            }

            if (followFlag) {
                boardRepository.increaseFollowCount(boardId);
            }
        }
    }

    @Transactional
    public void updateMembersBoardScore(Long boardId, @NonNull float score) {
        Member member = memberService.getCurrentMember();
        MembersBoard membersBoard = membersBoardRepository.findFirstByMemberIdAndBoardId(member.getMemberId(), boardId);
        if (membersBoard != null) {
            membersBoard.setScore(score);
            membersBoardRepository.save(membersBoard);
        } else {
            membersBoard = MembersBoard.builder()
                                       .memberBoardId(IdGenerator.generate())
                                       .memberId(member.getMemberId())
                                       .boardId(boardId)
                                       .likeFlag(false)
                                       .solveFlag(false)
                                       .followFlag(false)
                                       .score(score)
                                       .build();
            membersBoardRepository.save(membersBoard);
        }

    }
}
