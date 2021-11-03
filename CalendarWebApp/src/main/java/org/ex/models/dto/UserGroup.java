package org.ex.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_group")
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class UserGroup {

    @Id
    @GeneratedValue
    private int id;

    private int user_id;

    private int group_id;
}
