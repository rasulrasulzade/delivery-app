package com.company.orderms.entity;

import com.company.orderms.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "order_table")
public class OrderEntity {
    @Id
    private UUID id;
    private String comment;
    private Double pickupLat;
    private Double pickupLong;
    private Double destinationLat;
    private Double destinationLong;
    private OrderStatus status;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "courier_id")
    private UUID courierId;
}
