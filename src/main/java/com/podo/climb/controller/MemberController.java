package com.podo.climb.controller;

import com.podo.climb.model.request.CreateMemberRequest;
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
    public String createMember(@RequestBody CreateMemberRequest createMemberRequest) {
        memberService.createMember(createMemberRequest);
        return "success";
    }

}
