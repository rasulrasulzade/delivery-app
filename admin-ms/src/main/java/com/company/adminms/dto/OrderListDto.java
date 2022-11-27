package com.company.adminms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderListDto {
    private List<OrderDto> orders;
    private int page;
    private int pageSize;
    private boolean isLast;
    private long totalCount;
    private int totalPages;
}
