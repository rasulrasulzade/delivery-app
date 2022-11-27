package com.company.orderms.dto;

import com.company.orderms.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderQueueDto implements Serializable {
    private static final long serialVersionUID = 3612203923256672213L;
    private UUID id;
    private OrderStatus status;
    private UUID courierId;
    private LocationDto destination;
}
