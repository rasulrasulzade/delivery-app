package com.company.adminms.dto;

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
    private static final long serialVersionUID = -9125879548678917038L;
    private UUID id;
    private UUID courierId;
    private OrderStatus status;
}
