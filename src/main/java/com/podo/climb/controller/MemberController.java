package com.podo.climb.controller;

import com.podo.climb.entity.Member;
import com.podo.climb.model.AuthenticationToken;
import com.podo.climb.model.request.MemberRequest;
import com.podo.climb.model.request.MembersGymRequest;
import com.podo.climb.model.request.SignInRequest;
import com.podo.climb.model.response.ApiResult;
import com.podo.climb.model.response.FileUploadResponse;
import com.podo.climb.model.response.MemberDetailResponse;
import com.podo.climb.model.response.MemberResponse;
import com.podo.climb.model.response.SuccessfulResult;
import com.podo.climb.service.AuthenticationService;
import com.podo.climb.service.FileUploadService;
import com.podo.climb.service.MemberService;
import com.podo.climb.service.MembersGymService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;
    private final MembersGymService membersGymService;
    private final FileUploadService fileUploadService;
    private final AuthenticationService authenticationService;


    @ApiOperation(value = "세션 사용자 정보")
    @GetMapping("/v1/members/me")
    public ApiResult<MemberDetailResponse> getMember() {
        Member member = memberService.getCurrentMember();
        return new SuccessfulResult<>(memberService.getMemberDetail(member.getMemberId()));
    }


    @GetMapping("/v1/members/{memberId}")
    public ApiResult<MemberDetailResponse> getMember(@PathVariable Long memberId) {
        return new SuccessfulResult<>(memberService.getMemberDetail(memberId));
    }


    @PostMapping("/v1/members")
    public ApiResult<MemberResponse> createMember(@RequestBody MemberRequest memberRequest,
                                                  @ApiIgnore HttpSession session) {
        Member member = memberService.createMember(memberRequest);
        SignInRequest signInRequest = SignInRequest.builder()
                                                   .emailAddress(memberRequest.getEmailAddress())
                                                   .oauthType(memberRequest.getOauthType())
                                                   .password(memberRequest.getPassword())
                                                   .token(memberRequest.getToken())
                                                   .build();
        AuthenticationToken authenticationToken = authenticationService.signIn(signInRequest, session);
        if (memberRequest.getGymIds() != null) {
            memberRequest.getGymIds().forEach(gymId -> membersGymService.createMembersGym(member, new MembersGymRequest(gymId)));
        }
        return new SuccessfulResult<>(new MemberResponse(member, authenticationToken.getToken()));
    }

    @ApiOperation(value = "비밀번호, oauth 정보 수정 불가")
    @PutMapping("/v1/members/{memberId}")
    public ApiResult<MemberResponse> updateMember(@PathVariable Long memberId,
                                                  @RequestBody MemberRequest memberRequest) {
        return new SuccessfulResult<>(new MemberResponse(memberService.updateMember(memberId, memberRequest), null));
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
