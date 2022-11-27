package com.company.userms.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderDto {
    private String comment;
    private LocationDto pickup;
    private LocationDto destination;
    private UUID userId;
}