package com.company.authms.model;

import com.company.authms.enums.CourierStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Courier {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private CourierStatus status;
}
