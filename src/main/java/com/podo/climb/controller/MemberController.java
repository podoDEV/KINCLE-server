package com.podo.climb.controller;

import com.podo.climb.entity.Member;
import com.podo.climb.model.request.CreateMemberRequest;
import com.podo.climb.model.response.ApiResult;
import com.podo.climb.model.response.SuccessfulResult;
import com.podo.climb.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    private MemberService memberService;

    @Autowired
    MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/v1/member")
    public ApiResult<Member> createMember(@RequestBody CreateMemberRequest createMemberRequest) {
        return new SuccessfulResult(memberService.createMember(createMemberRequest));
    }

}
