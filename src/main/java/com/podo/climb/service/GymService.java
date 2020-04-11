package com.podo.climb.service;

import com.podo.climb.Utils.IdGenerator;
import com.podo.climb.entity.Gym;
import com.podo.climb.entity.Member;
import com.podo.climb.model.request.CreateGymRequest;
import com.podo.climb.model.response.GymResponse;
import com.podo.climb.repository.GymRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GymService {

    private MemberService memberService;
    private GymRepository gymRepository;


    GymService(MemberService memberService,
               GymRepository gymRepository) {
        this.memberService = memberService;
        this.gymRepository = gymRepository;
    }

    @Transactional
    public Gym createGym(CreateGymRequest createGymRequest) {
        Member member = memberService.getCurrentMember();
        Gym gym = Gym.builder()
                     .gymId(IdGenerator.generate())
                     .name(createGymRequest.getName())
                     .description(createGymRequest.getDescription())
                     .address(createGymRequest.getAddress())
                     .imageUrl(createGymRequest.getImageUrl())
                     .openingHours(createGymRequest.getOpeningHours())
                     .creatorId(member.getMemberId())
                     .build();
        gymRepository.save(gym);
        return gym;
    }

    @Transactional
    public List<GymResponse> getGyms() {
        List<Gym> gyms = gymRepository.findAll();
        return gyms.stream().map(Gym::toGymResponse).collect(Collectors.toList());
    }
}
