package org.ex.controllers;

import org.ex.models.Task;
import org.ex.services.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "task")
@CrossOrigin(origins = "${angular.url}")
public class TaskController {

    private final Logger LOGGER= LoggerFactory.getLogger(this.getClass());
    private TaskService service;

    @Autowired
    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = this.service.getAllTasks();

        if(tasks != null) {
            if(tasks.size() > 0) {
                LOGGER.debug("Returning list of all tasks");
                return ResponseEntity.ok().body(tasks);
            }
        }

        LOGGER.debug("Failed to find any tasks, status not found");
        return ResponseEntity.notFound().build();
    }

    @PostMapping(path = "/new")
    @Transactional
    public ResponseEntity createUserTask(@RequestBody Task task) {

        boolean status = this.service.createTask(task);

        if(status) {
            LOGGER.debug("Created new task");
            return ResponseEntity.ok().build();
        }

        LOGGER.debug("Failed to create task " + task);
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(path = "/user/{id}")
    public ResponseEntity<List<Task>> getTasksByUserId(@PathVariable int id) {
        List<Task> tasks = this.service.getTasksByUserId(id);

        if(tasks != null) {
            if(tasks.size() > 0) {
                LOGGER.debug("Returning tasks for user_id: " + id);
                return ResponseEntity.ok().body(tasks);
            }
        }

        LOGGER.debug("Failed to find any tasks for user_id: " + id);
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(path = "/group/{id}")
    public ResponseEntity<List<Task>> getTasksByGroupId(@PathVariable int id) {
        List<Task> tasks = this.service.getTasksByGroupId(id);

        if(tasks != null) {
            if(tasks.size() > 0) {
                LOGGER.debug("Returning tasks for group_id: " + id);
                return ResponseEntity.ok().body(tasks);
            }
        }

        LOGGER.debug("Failed to find any tasks for group_id: " + id);
        return ResponseEntity.notFound().build();
    }

    @PutMapping(path = "/update")
    @Transactional
    public ResponseEntity updateTask(@RequestBody Task task) {

        boolean status = this.service.updateTask(task);

        if(status) {
            LOGGER.debug("Successfully updated task " + task.getTask_name());
            return ResponseEntity.ok().build();
        }

        LOGGER.debug("Failed to update task " + task);
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping(path = "/delete/{id}")
    @Transactional
    public ResponseEntity deleteTask(@PathVariable int id) {

        boolean status = this.service.deleteTask(id);

        Task task = this.service.getTaskById(id);

        if(status && task != null) {
            LOGGER.debug("Successfully deleted task with id: " + id);
            return ResponseEntity.ok().build();
        }

        LOGGER.debug("Failed to delete task, task with id " + id + " doesn't exist");
        return ResponseEntity.status(410).build();
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable int id) {
        Task task = this.service.getTaskById(id);
        if(task != null) {
            LOGGER.debug("Returning task with id: " + id);
            return ResponseEntity.ok().body(task);
        }

        LOGGER.debug("Failed to find task with id: " + id);
        return ResponseEntity.notFound().build();
    }

}
