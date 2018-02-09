package com.baciu.filestorage.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "users")
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, unique = true, nullable = false)
    private Long id;

    @Column
    private String name;

    @ManyToMany(fetch = FetchType.EAGER,  mappedBy = "roles")
    private Set<User> users = new HashSet<>(0);

}
