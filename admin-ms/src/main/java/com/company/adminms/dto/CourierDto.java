package com.company.adminms.dto;

import com.company.adminms.enums.CourierStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourierDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private CourierStatus status;
}
