package com.company.authms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfo {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<String> roles;
}
