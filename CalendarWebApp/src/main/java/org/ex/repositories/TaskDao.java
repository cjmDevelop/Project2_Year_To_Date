package org.ex.repositories;

import org.ex.models.Task;
import org.ex.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TaskDao extends JpaRepository<Task, Long> {

    @Query(value = "select * from task where id = :id", nativeQuery = true)
    Task getTaskById(@Param("id") int id);

    @Query(value = "select * from task", nativeQuery = true)
    List<Task> getAllTasks();

    @Query(value = "select * from task where user_id = :id", nativeQuery = true)
    List<Task> getTasksByUserId(@Param("id") int id);

    @Query(value = "select * from task where group_id = :id", nativeQuery = true)
    List<Task> getTasksByGroupId(@Param("id") int id);

    @Modifying
    @Query(value = "insert into task (user_id, task_name, description, start_date, end_date)\n" +
            "values (:userId, :taskName, :description, :startDate, :endDate)", nativeQuery = true)
    void insertNewUserTask(@Param("userId") Integer userId, @Param("taskName") String name, @Param("description") String description, @Param("startDate")Timestamp startDate, @Param("endDate") Timestamp endDate);

    @Modifying
    @Query(value = "insert into task (group_id, task_name, description, start_date, end_date)\n" +
            "values (:groupId, :taskName, :description, :startDate, :endDate)", nativeQuery = true)
    void insertNewGroupTask(@Param("groupId") Integer groupId, @Param("taskName") String name, @Param("description") String description, @Param("startDate")Timestamp startDate, @Param("endDate") Timestamp endDate);

    @Modifying
    @Query(value = "UPDATE task\n" +
            "SET task_name = :taskName, description= :description, start_date = :startDate, end_date = :endDate, status = :status, minutes_worked = :minWorked\n" +
            "WHERE id = :id", nativeQuery = true)
    void updateTask(@Param("id") int id, @Param("taskName") String name, @Param("description") String description, @Param("startDate")Timestamp startDate, @Param("endDate") Timestamp endDate, @Param("status") String status, @Param("minWorked") int minWorked);

    @Modifying
    @Query(value = "DELETE FROM task WHERE id= :id", nativeQuery = true)
    void deleteTask(@Param("id") int id);
}
