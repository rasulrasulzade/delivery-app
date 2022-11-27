package com.company.courierms.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CourierOrderParams {
    private UUID courierId;
    private Integer page;
    private Integer pageSize;
    private String direction;
    private String sortBy;
}
