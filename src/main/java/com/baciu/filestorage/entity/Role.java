package com.baciu.filestorage.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
