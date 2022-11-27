package com.company.courierms.dto;

import com.company.courierms.enums.OrderStatus;
import lombok.Data;

@Data
public class ChangeOrderStatusDto {
    private final OrderStatus orderStatus;
}
