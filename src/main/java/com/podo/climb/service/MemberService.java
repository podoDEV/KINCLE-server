package com.podo.climb.service;

import com.podo.climb.entity.Gym;
import com.podo.climb.entity.Member;
import com.podo.climb.entity.MembersBoard;
import com.podo.climb.entity.MembersGym;
import com.podo.climb.exception.ResourceNotFoundException;
import com.podo.climb.model.request.MemberRequest;
import com.podo.climb.model.response.MemberDetailResponse;
import com.podo.climb.repository.GymRepository;
import com.podo.climb.repository.MemberRepository;
import com.podo.climb.repository.MembersBoardRepository;
import com.podo.climb.repository.MembersGymRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final MembersGymRepository membersGymRepository;
    private final MembersBoardRepository membersBoardRepository;
    private final GymRepository gymRepository;

    @Transactional
    public Member getMember(Long memberId) {
        return Optional.ofNullable(memberRepository.findByMemberId(memberId)).orElseThrow(ResourceNotFoundException::new);
    }


    @Transactional
    public Member createMember(MemberRequest memberRequest) {
        Member member = new Member(memberRequest);
        return memberRepository.saveAndFlush(member);
    }

    @Transactional
    public Member updateMember(Long memberId, MemberRequest memberRequest) {
        Member member = Optional.ofNullable(memberRepository.findByMemberId(memberId)).orElseThrow(ResourceNotFoundException::new);
        member.updateMember(memberRequest);
        memberRepository.saveAndFlush(member);
        return member;
    }

    @Transactional
    public MemberDetailResponse getMemberDetail(Long memberId) {
        Member member = this.getMember(memberId);
        List<Long> gymIds = membersGymRepository.findByMemberId(member.getMemberId())
                                                .stream()
                                                .map(MembersGym::getGymId)
                                                .collect(Collectors.toList());
        List<Gym> gyms = gymRepository.findByGymIdIn(gymIds);
        List<MembersBoard> membersBoards = membersBoardRepository.findByMemberId(memberId);
        return new MemberDetailResponse(member, gyms, membersBoards);

    }


    public Member findByEmailAddress(String emailAddress) {
        return memberRepository.findByEmailAddress(emailAddress);
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
