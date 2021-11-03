package org.ex.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_group")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class Group {

    @Id
    @GeneratedValue
    private int id;

    private String group_name;

    private String description;

    private int created_by;
}
