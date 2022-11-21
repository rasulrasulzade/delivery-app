package com.company.orderms.dto;

import com.company.orderms.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueueOrderDto {
    private UUID id;
    private OrderStatus status;
    private UUID courierId;
}
