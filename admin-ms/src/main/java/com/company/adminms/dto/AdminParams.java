package com.company.adminms.dto;

import lombok.Data;

@Data
public class AdminParams {
    private Integer page;
    private Integer pageSize;
    private String direction;
    private String sortBy;
}
