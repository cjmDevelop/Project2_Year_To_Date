package org.ex.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_user")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue
    private int id;

    private String first_name;

    private String last_name;

    private String user_name;

    @Enumerated(EnumType.STRING)
    private UserType user_type;

    private String email;

    private String user_password;

}
