package com.podo.climb.controller;

import com.podo.climb.entity.Gym;
import com.podo.climb.model.request.MembersGymRequest;
import com.podo.climb.model.response.ApiResult;
import com.podo.climb.model.response.SuccessfulResult;
import com.podo.climb.service.MembersGymService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MembersGymController {

    private final MembersGymService membersGymService;

    @Autowired
    MembersGymController(MembersGymService membersGymService) {
        this.membersGymService = membersGymService;
    }

    @ApiOperation(value = "즐겨찾는 암장 등록")
    @PostMapping("/v1/members/gyms")
    public ApiResult<?> createMembersGym(@RequestBody MembersGymRequest membersGymRequest) {
        membersGymService.createMembersGym(membersGymRequest);
        return new SuccessfulResult<>();
    }

    @ApiOperation(value = "즐겨찾는 암장 삭제")
    @DeleteMapping("/v1/members/gyms")
    public ApiResult<?> deleteMembersGym(@RequestBody MembersGymRequest membersGymRequest) {
        membersGymService.deleteMembersGym(membersGymRequest);
        return new SuccessfulResult<>();
    }

    @ApiOperation(value = "현재 사용자의 즐겨찾는 암장 모두 조회")
    @GetMapping("/v1/members/gyms")
    public ApiResult<List<Gym>> getMembersGyms() {
        return new SuccessfulResult<>(membersGymService.getMembersGyms());
    }


}