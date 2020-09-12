package com.podo.climb.controller;

import com.podo.climb.entity.Member;
import com.podo.climb.model.request.MemberRequest;
import com.podo.climb.model.response.ApiResult;
import com.podo.climb.model.response.FileUploadResponse;
import com.podo.climb.model.response.SuccessfulResult;
import com.podo.climb.service.AuthenticationService;
import com.podo.climb.service.FileUploadService;
import com.podo.climb.service.MemberService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class MemberController {

    private final MemberService memberService;
    private final FileUploadService fileUploadService;
    private final AuthenticationService authenticationService;

    @Autowired
    MemberController(MemberService memberService,
                     FileUploadService fileUploadService,
                     AuthenticationService authenticationService) {
        this.memberService = memberService;
        this.fileUploadService = fileUploadService;
        this.authenticationService = authenticationService;
    }

    @ApiOperation(value = "세션 사용자 정보")
    @GetMapping("/v1/members/me")
    public ApiResult<Member> getMember() {
        return new SuccessfulResult<>(memberService.getCurrentMember());
    }

    @GetMapping("/v1/members/{memberId}")
    public ApiResult<Member> getMember(@PathVariable Long memberId) {
        return new SuccessfulResult<>(memberService.getMember(memberId));
    }


    @PostMapping("/v1/members")
    public ApiResult<Member> createMember(@RequestBody MemberRequest memberRequest) {
        return new SuccessfulResult<>(memberService.createMember(memberRequest));
    }

    @ApiOperation(value = "비밀번호, oauth 정보 수정 불가")
    @PutMapping("/v1/members/{memberId}")
    public ApiResult<Member> updateMember(@PathVariable Long memberId,
                                          @RequestBody MemberRequest memberRequest) {
        return new SuccessfulResult<>(memberService.updateMember(memberId, memberRequest));
    }


    @ApiOperation(value = "프로필 이미지 업로드, 썸네일 처리")
    @PostMapping("/v1/members/profile-image")
    public ApiResult<FileUploadResponse> upload(@RequestParam("image") MultipartFile file) {
        return new SuccessfulResult<>(fileUploadService.restoreProfileImage(file));
    }


    @PostMapping("/v1/init-password")
    public ApiResult<?> initPassword(@RequestParam String emailAddress) throws Exception {
        authenticationService.initPassword(emailAddress);
        return new SuccessfulResult<>();
    }

}
