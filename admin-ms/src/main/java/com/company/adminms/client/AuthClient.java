package com.company.adminms.client;

import com.company.adminms.dto.CourierListDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "AuthClient", url = "http://localhost:8888/api/auth/v1/couriers")
public interface AuthClient {

    @GetMapping
    CourierListDto getCouriers();
}