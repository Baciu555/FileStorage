package com.baciu.filestorage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
