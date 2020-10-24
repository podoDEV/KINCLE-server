package com.podo.climb.service;

import com.podo.climb.Utils.IdGenerator;
import com.podo.climb.entity.Gym;
import com.podo.climb.entity.Member;
import com.podo.climb.entity.MembersGym;
import com.podo.climb.model.request.MembersGymRequest;
import com.podo.climb.repository.GymRepository;
import com.podo.climb.repository.MembersGymRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MembersGymService {

    private final MemberService memberService;
    private final GymRepository gymRepository;
    private final MembersGymRepository membersGymRepository;

    MembersGymService(MemberService memberService,
                      GymRepository gymRepository,
                      MembersGymRepository membersGymRepository) {
        this.memberService = memberService;
        this.gymRepository = gymRepository;
        this.membersGymRepository = membersGymRepository;
    }

    @Transactional
    public List<Gym> getMembersGyms() {
        Member member = memberService.getCurrentMember();
        List<Long> gymIds = membersGymRepository.findByMemberId(member.getMemberId())
                                                .stream()
                                                .map(MembersGym::getGymId)
                                                .collect(Collectors.toList());
        return gymRepository.findByGymIdIn(gymIds);
    }

    @Transactional
    public void createMembersGym(MembersGymRequest membersGymRequest) {
        Member member = memberService.getCurrentMember();
        this.createMembersGym(member, membersGymRequest);

    }

    @Transactional
    public void createMembersGym(Member member, MembersGymRequest membersGymRequest) {
        if (membersGymRepository.findByMemberIdAndGymId(member.getMemberId(), membersGymRequest.getGymId()) == null) {
            MembersGym memberFavorite = MembersGym.builder()
                                                  .membersGymId(IdGenerator.generate())
                                                  .memberId(member.getMemberId())
                                                  .gymId(membersGymRequest.getGymId())
                                                  .build();
            membersGymRepository.save(memberFavorite);
        }
    }

    @Transactional
    public void deleteMembersGym(MembersGymRequest membersGymRequest) {
        Member member = memberService.getCurrentMember();
        membersGymRepository.deleteByMemberIdAndAndGymId(member.getMemberId(), membersGymRequest.getGymId());

    }


}
