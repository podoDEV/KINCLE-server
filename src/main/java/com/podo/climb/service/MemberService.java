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
        Member member = new Member();
        Long memberId = IdGenerator.generate();
        member.setMemberId(memberId);
        member.setNickname(createMemberRequest.getNickname());
        Sha256PasswordEncoder sha256PasswordEncoder = new Sha256PasswordEncoder();
        member.setPassword(sha256PasswordEncoder.encode(createMemberRequest.getPassword()));
        member.setEmailAddress(createMemberRequest.getEmailAddress());
        member.setOauthType(createMemberRequest.getOauthType());
        MemberRole memberRole = new MemberRole();
        memberRole.setMemberId(memberId);
        memberRole.setRole(MemberRoleType.MEMBER);
        member.setMemberRole(Collections.singletonList(memberRole));
        memberRepository.saveAndFlush(member);
        return member;
    }

    private Member createOauthMember(CreateMemberRequest createMemberRequest) {
        Member member = new Member();
        Long memberId = IdGenerator.generate();
        member.setMemberId(memberId);
        member.setNickname(createMemberRequest.getNickname());
        member.setPassword(createMemberRequest.getToken());
        member.setEmailAddress(createMemberRequest.getEmailAddress());
        member.setToken(createMemberRequest.getToken());
        member.setOauthType(createMemberRequest.getOauthType());
        MemberRole memberRole = new MemberRole();
        memberRole.setMemberId(memberId);
        memberRole.setRole(MemberRoleType.MEMBER);
        member.setMemberRole(Collections.singletonList(memberRole));
        memberRepository.saveAndFlush(member);
        return member;
    }


}
