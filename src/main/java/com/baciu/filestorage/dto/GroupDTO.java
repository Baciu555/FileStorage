package com.baciu.filestorage.dto;

import lombok.Data;

import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class GroupDTO {

    private Long id;
    @Size(min=0, max=30, message="Nazwa grupy nie może być dłuższa niż 30 znaków")
    private String name;
    private String description;
    private Set<UserDTO> users;
    private Set<FileDTO> files;
    private Integer membersCount;
    private Integer filesCount;
}
