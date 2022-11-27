package com.company.userms.client.dto;

import com.company.userms.client.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueueDto {
    private OrderStatus status;
    private LocationDto destination;
}
