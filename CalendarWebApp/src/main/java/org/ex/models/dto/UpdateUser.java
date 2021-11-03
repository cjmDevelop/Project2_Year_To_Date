package org.ex.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.ex.models.UserType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_user")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class UpdateUser {

    @Id
    private int id;

    private String first_name;

    private String last_name;

    private String user_name;

    private String email;

    private String user_password;
}
