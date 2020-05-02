package com.podo.climb.controller;

import com.podo.climb.entity.Gym;
import com.podo.climb.model.request.CreateGymRequest;
import com.podo.climb.model.response.ApiResult;
import com.podo.climb.model.response.GymResponse;
import com.podo.climb.model.response.SuccessfulResult;
import com.podo.climb.service.GymService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GymController {

    private GymService gymService;

    @Autowired
    GymController(GymService gymService) {
        this.gymService = gymService;
    }

    @ApiOperation(value = "암장 정보 생성")
    @PostMapping("/v1/gyms")
    public ApiResult<Gym> createGym(@RequestBody CreateGymRequest createGymRequest) {
        return new SuccessfulResult<>(gymService.createGym(createGymRequest));
    }

    @ApiOperation(value = "전체 암장 정보 조회")
    @GetMapping("/v1/gyms")
    public ApiResult<List<GymResponse>> getGyms() {
        return new SuccessfulResult<>(gymService.getGyms());
    }
}
