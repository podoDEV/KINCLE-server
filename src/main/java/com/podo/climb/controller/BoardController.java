package com.podo.climb.controller;

import com.podo.climb.entity.Board;
import com.podo.climb.exception.BadRequestException;
import com.podo.climb.model.BoardFilterType;
import com.podo.climb.model.Inner.MembersBoardFlag;
import com.podo.climb.model.MembersBoardFlagType;
import com.podo.climb.model.request.BoardRequest;
import com.podo.climb.model.request.MembersBoardScoreRequest;
import com.podo.climb.model.response.ApiResult;
import com.podo.climb.model.response.BoardResponse;
import com.podo.climb.model.response.CommentResponse;
import com.podo.climb.model.response.SuccessfulResult;
import com.podo.climb.service.BoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = {"게시물(문제) API"})
@RestController
public class BoardController {

    private final BoardService boardService;

    @Autowired
    BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @ApiOperation(value = "문제 생성")
    @PostMapping("/v1/boards")
    public ApiResult<Board> createBoard(
            @RequestBody BoardRequest boardRequest) {
        return new SuccessfulResult<>(boardService.createBoard(boardRequest));
    }

    @ApiOperation(value = "게시물 단건 조회")
    @GetMapping("/v1/boards/{boardId}")
    public ApiResult<BoardResponse> getBoard(@PathVariable Long boardId) {
        return new SuccessfulResult<>(boardService.getBoard(boardId));
    }

    @ApiOperation(value = "문제 수정")
    @PutMapping("/v1/boards/{boardId}")
    public ApiResult<BoardResponse> updateBoard(@PathVariable Long boardId, BoardRequest boardRequest) {
        return new SuccessfulResult<>(boardService.updateBoard(boardId, boardRequest));
    }

    @DeleteMapping("/v1/board/{boardId}")
    public ApiResult<?> deleteBoard(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
        return new SuccessfulResult<>();
    }

    @ApiOperation(value = "게시물 조회", notes = "pageable, sort=createdAt,likeCount,followCount")
    @GetMapping("/v1/boards")
    public ApiResult<Page<BoardResponse>> getBoards(@ApiParam(value = "암장으로 필터, 없을 경우 전체") @RequestParam(required = false) Long gymId,
                                                    @ApiParam(value = "register, like, solve, follow, 없을 경우 전체") @RequestParam(required = false) BoardFilterType boardFilterType,
                                                    @ApiIgnore Pageable pageable) {

        return new SuccessfulResult<>(boardService.getBoards(gymId, boardFilterType, pageable));
    }

    @ApiOperation(value = "게시물 좋아요 여부, 문제 성공 여부, 저장 여부 수정")
    @PutMapping("/v1/boards/{boardId}/flag/{flag}")
    public ApiResult<?> updateMembersBoard(@PathVariable Long boardId,
                                           @ApiParam(value = "like, dislike, solved, unsolved, follow, unfollow") @PathVariable MembersBoardFlagType flag) {

        boardService.updateMembersBoard(boardId, new MembersBoardFlag(flag));
        return new SuccessfulResult<>();
    }

    @ApiOperation(value = "문제 점수 수정")
    @PutMapping("/v1/boards/{boardId}/score")
    public ApiResult<?> updateMembersBoardScore(@PathVariable Long boardId,
                                                @ApiParam(value = "점수") @RequestBody MembersBoardScoreRequest membersBoardScoreRequest) {

        if (membersBoardScoreRequest.getScore() == null || membersBoardScoreRequest.getScore() > 5 || membersBoardScoreRequest.getScore() < 0)
            throw new BadRequestException();

        boardService.updateMembersBoardScore(boardId, membersBoardScoreRequest.getScore());
        return new SuccessfulResult<>();
    }

    @ApiOperation(value = "문제 점수 수정", notes = "pageable")
    @GetMapping("/v1/boards/{boardId}/comments")
    public ApiResult<Page<CommentResponse>> getComments(@PathVariable Long boardId, @ApiIgnore Pageable pageable) {
        return new SuccessfulResult<>(boardService.getComments(boardId, pageable));
    }


}
