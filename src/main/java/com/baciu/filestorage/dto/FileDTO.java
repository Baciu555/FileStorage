package com.baciu.filestorage.dto;

import com.baciu.filestorage.entity.Group;
import com.baciu.filestorage.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
public class FileDTO {

    private Long id;
    private String path;

    @Size(min=0, max=30, message="Nazwa pliku nie może być dłuższa niż 30 znaków")
    private String name;
    private String description;
    private Date uploadDate;
    private Long size;
    private UserDTO user;
    private Set<GroupDTO> groups;
}
