package com.company.userms.dto;

import com.company.userms.client.dto.LocationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderDto {
    private String comment;
    private LocationDto pickup;
    private LocationDto destination;
}