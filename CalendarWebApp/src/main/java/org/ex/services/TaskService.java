package org.ex.services;

import org.ex.models.Task;

import java.util.List;

public interface TaskService {

    List<Task> getAllTasks();

    Task getTaskById(int id);

    List<Task> getTasksByUserId(int id);

    List<Task> getTasksByGroupId(int id);

    boolean createTask(Task task);

    boolean updateTask(Task task);

    boolean deleteTask(int id);
}
