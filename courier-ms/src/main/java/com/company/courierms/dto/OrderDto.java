package com.company.courierms.dto;

import com.company.courierms.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {
    private UUID id;
    private String comment;
    private LocationDto pickup;
    private LocationDto destination;
    private OrderStatus status;
    private UUID userId;
    private UUID courierId;
}
