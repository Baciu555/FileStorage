package com.baciu.filestorage.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "groups_users", joinColumns = {
            @JoinColumn(name = "groups_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "users_id", nullable = false)})
    private Set<User> users = new HashSet<>(0);

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "file_group", joinColumns = {
            @JoinColumn(name = "group_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "file_id", nullable = false)
            })
    private Set<File> files = new HashSet<>(0);
}
