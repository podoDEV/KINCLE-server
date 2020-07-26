package com.podo.climb.controller;

import com.podo.climb.entity.Member;
import com.podo.climb.model.request.CreateMemberRequest;
import com.podo.climb.model.response.ApiResult;
import com.podo.climb.model.response.FileUploadResponse;
import com.podo.climb.model.response.SuccessfulResult;
import com.podo.climb.service.AuthenticationService;
import com.podo.climb.service.FileUploadService;
import com.podo.climb.service.MemberService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/v1/members")
    public ApiResult<Member> createMember(@RequestBody CreateMemberRequest createMemberRequest) {
        return new SuccessfulResult(memberService.createMember(createMemberRequest));
    }

    @ApiOperation(value = "프로필 이미지 업로드, 썸네일 처리")
    @PostMapping("/v1/members/profile-image")
    public ApiResult<FileUploadResponse> upload(@RequestParam("image") MultipartFile file) {
        return new SuccessfulResult(fileUploadService.restoreProfileImage(file));
    }


    @PostMapping("/v1/init-password")
    public ApiResult initPassword(@RequestParam String emailAddress) throws Exception {
        authenticationService.initPassword(emailAddress);
        return new SuccessfulResult();
    }

}
