package com.company.authms.entity;

import com.company.authms.enums.CourierStatus;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "courier_info")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourierInfoEntity {
    @Id
    @GeneratedValue
    private UUID id;
    private CourierStatus status;

    @OneToOne(mappedBy = "courierInfo")
    private UserEntity user;
}
