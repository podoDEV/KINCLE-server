package com.podo.climb.service;

import com.podo.climb.Utils.IdGenerator;
import com.podo.climb.entity.Gym;
import com.podo.climb.entity.Member;
import com.podo.climb.entity.MemberFavorite;
import com.podo.climb.model.request.MemberFavoriteRequest;
import com.podo.climb.repository.GymRepository;
import com.podo.climb.repository.MemberFavoriteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberFavoriteService {

    private MemberService memberService;
    private GymRepository gymRepository;
    private MemberFavoriteRepository memberFavoriteRepository;


    MemberFavoriteService(MemberService memberService,
                          GymRepository gymRepository,
                          MemberFavoriteRepository memberFavoriteRepository) {
        this.memberService = memberService;
        this.gymRepository = gymRepository;
        this.memberFavoriteRepository = memberFavoriteRepository;
    }

    @Transactional
    public List<Gym> getFavorites() {
        Member member = memberService.getCurrentMember();
        List<Long> gymIds = memberFavoriteRepository.findByMemberId(member.getMemberId())
                                                    .stream()
                                                    .map(MemberFavorite::getGymId)
                                                    .collect(Collectors.toList());
        return gymRepository.findByGymIdIn(gymIds);
    }

    @Transactional
    public void createMemberFavorite(MemberFavoriteRequest memberFavoriteRequest) {
        Member member = memberService.getCurrentMember();
        if (memberFavoriteRepository.findByMemberIdAndGymId(member.getMemberId(), memberFavoriteRequest.getGymId()) == null) {
            MemberFavorite memberFavorite = MemberFavorite.builder()
                                                          .memberFavoriteId(IdGenerator.generate())
                                                          .memberId(member.getMemberId())
                                                          .gymId(memberFavoriteRequest.getGymId())
                                                          .build();
            memberFavoriteRepository.save(memberFavorite);
        }
    }

    @Transactional
    public void deleteMemberFavorite(MemberFavoriteRequest memberFavoriteRequest) {
        Member member = memberService.getCurrentMember();
        memberFavoriteRepository.deleteByMemberIdAndAndGymId(member.getMemberId(), memberFavoriteRequest.getGymId());


    }


}
