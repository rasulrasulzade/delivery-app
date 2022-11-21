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
public class UpdateDtoRequest {
    private OrderStatus status;
    private UUID courierId;
}
