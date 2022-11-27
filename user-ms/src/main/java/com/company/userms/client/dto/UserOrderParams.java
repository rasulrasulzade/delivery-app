package com.company.userms.client.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserOrderParams {
    private UUID userId;
    private Integer page;
    private Integer pageSize;
    private String direction;
    private String sortBy;
}
