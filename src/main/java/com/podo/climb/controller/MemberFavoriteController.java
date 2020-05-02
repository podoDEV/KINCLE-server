package com.podo.climb.controller;

import com.podo.climb.entity.Gym;
import com.podo.climb.model.request.MemberFavoriteRequest;
import com.podo.climb.model.response.ApiResult;
import com.podo.climb.model.response.SuccessfulResult;
import com.podo.climb.service.MemberFavoriteService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MemberFavoriteController {

    private MemberFavoriteService memberFavoriteService;

    @Autowired
    MemberFavoriteController(MemberFavoriteService memberFavoriteService) {
        this.memberFavoriteService = memberFavoriteService;
    }

    @ApiOperation(value = "즐겨찾는 암장 등록")
    @PostMapping("/v1/members/favorite")
    public ApiResult createMemberFavorite(@RequestBody MemberFavoriteRequest memberFavoriteRequest) {
        memberFavoriteService.createMemberFavorite(memberFavoriteRequest);
        return new SuccessfulResult();
    }

    @ApiOperation(value = "즐겨찾는 암장 삭제")
    @DeleteMapping("/v1/members/favorite")
    public ApiResult deleteMemberFavorite(@RequestBody MemberFavoriteRequest memberFavoriteRequest) {
        memberFavoriteService.deleteMemberFavorite(memberFavoriteRequest);
        return new SuccessfulResult();
    }

    @ApiOperation(value = "현재 사용자의 즐겨찾는 암장 모두 조회")
    @GetMapping("/v1/members/favorite")
    public ApiResult<List<Gym>> getMemberFavorites() {
        return new SuccessfulResult<>(memberFavoriteService.getFavorites());
    }



}