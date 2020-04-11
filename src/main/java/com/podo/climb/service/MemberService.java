package com.podo.climb.service;

import com.podo.climb.Utils.IdGenerator;
import com.podo.climb.encoder.Sha256PasswordEncoder;
import com.podo.climb.entity.Member;
import com.podo.climb.entity.MemberRole;
import com.podo.climb.exception.ApiFailedException;
import com.podo.climb.model.MemberRoleType;
import com.podo.climb.model.OauthType;
import com.podo.climb.model.request.CreateMemberRequest;
import com.podo.climb.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class MemberService {

    private MemberRepository memberRepository;

    @Autowired
    MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Member createMember(CreateMemberRequest createMemberRequest) {
        OauthType oauthType = createMemberRequest.getOauthType();
        if (OauthType.SELF.equals(oauthType)) {
            return createSelfMember(createMemberRequest);
        } else if (OauthType.GOOGLE.equals(oauthType) || OauthType.APPLE.equals(oauthType)) {
            return createOauthMember(createMemberRequest);
        } else {
            throw new ApiFailedException(400, "제공하지 않는 로그인 타입입니다.");
        }
    }

    private Member createSelfMember(CreateMemberRequest createMemberRequest) {
        Long memberId = IdGenerator.generate();
        Sha256PasswordEncoder sha256PasswordEncoder = new Sha256PasswordEncoder();
        Member member = Member.builder()
                              .memberId(memberId)
                              .nickname(createMemberRequest.getNickname())
                              .password(sha256PasswordEncoder.encode(createMemberRequest.getPassword()))
                              .emailAddress(createMemberRequest.getEmailAddress())
                              .oauthType(createMemberRequest.getOauthType())
                              .build();

        MemberRole memberRole = MemberRole.builder()
                                          .memberId(memberId)
                                          .role(MemberRoleType.MEMBER)
                                          .build();

        member.setMemberRole(Collections.singletonList(memberRole));
        memberRepository.saveAndFlush(member);
        return member;
    }

    private Member createOauthMember(CreateMemberRequest createMemberRequest) {
        Long memberId = IdGenerator.generate();
        Sha256PasswordEncoder sha256PasswordEncoder = new Sha256PasswordEncoder();
        Member member = Member.builder()
                              .memberId(memberId)
                              .nickname(createMemberRequest.getNickname())
                              .password(sha256PasswordEncoder.encode(createMemberRequest.getToken()))
                              .emailAddress(createMemberRequest.getEmailAddress())
                              .token(createMemberRequest.getToken())
                              .oauthType(createMemberRequest.getOauthType())
                              .build();

        MemberRole memberRole = MemberRole.builder()
                                          .memberId(memberId)
                                          .role(MemberRoleType.MEMBER)
                                          .build();

        member.setMemberRole(Collections.singletonList(memberRole));
        memberRepository.saveAndFlush(member);
        return member;
    }

    //TODO: 시스템 어드민 추가가 어려움에 따라 정보를 못 가져오는 경우(테스트), 특정 계정을 사용함
    @Transactional
    public Member getCurrentMember() {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return memberRepository.findByMemberId(Long.valueOf(user.getUsername()));
        } catch (Exception e) {
            return memberRepository.findByMemberId(2720638695768271459L);
        }
    }


}
