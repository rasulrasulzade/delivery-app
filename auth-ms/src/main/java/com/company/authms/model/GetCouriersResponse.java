package com.company.authms.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetCouriersResponse {
    private List<Courier> couriers;
}
