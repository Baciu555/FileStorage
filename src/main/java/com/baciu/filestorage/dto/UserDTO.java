package com.baciu.filestorage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;

    @Size(min=3, max=30, message="Długość loginu powinna mieścic się w granicach 3 - 30 znaków")
    private String username;

    @Email(message = "niepoprawny format email")
    @NotEmpty(message = "niepoprawny format email")
    private String email;

    @Size(min=3, max=30, message="Długość hasła powinna mieścic się w granicach 3 - 30 znaków")
    private String password;

    private Date registerDate;
    private Set<FileDTO> files;
    private Set<GroupDTO> groups;
    private Set<RoleDTO> roles;

}
