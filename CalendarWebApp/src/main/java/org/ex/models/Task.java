package org.ex.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "task")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class Task {

    @Id
    @GeneratedValue
    private int id;

    private Integer user_id;

    private Integer group_id;

    private String task_name;

    private String description;

    private Timestamp start_date;

    private Timestamp end_date;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private int minutes_worked;
}
