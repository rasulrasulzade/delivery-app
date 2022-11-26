package com.company.authms.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="role")
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class RoleEntity {

    @Id
    private UUID id;

    @Column
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<UserEntity> users = new ArrayList<>();
}
