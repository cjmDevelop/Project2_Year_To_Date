package org.ex.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.ex.models.User;
import org.ex.models.UserType;
import org.springframework.context.annotation.Scope;

import javax.persistence.*;

@Entity
@Table(name = "t_user")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class SessionUser {

    public SessionUser(User user) {
        this.id = user.getId();
        this.first_name = user.getFirst_name();
        this.last_name = user.getLast_name();
        this.user_name = user.getUser_name();
        this.user_type = user.getUser_type();
        this.email = user.getEmail();
    }

    @Id
    private int id;

    private String first_name;

    private String last_name;

    private String user_name;

    @Enumerated(EnumType.STRING)
    private UserType user_type;

    private String email;

}
