package com.company.authms.controller;

import com.company.authms.model.GetCouriersResponse;
import com.company.authms.service.CourierService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/couriers")
@RequiredArgsConstructor
public class CourierController {
    private final CourierService courierService;

    @GetMapping()
    public GetCouriersResponse getCouriers() {
       return GetCouriersResponse.builder().couriers(courierService.getCouriers()).build();
    }
}
