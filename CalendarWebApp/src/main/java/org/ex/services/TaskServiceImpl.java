package org.ex.services;

import org.ex.models.Group;
import org.ex.models.Task;
import org.ex.models.User;
import org.ex.repositories.GroupDao;
import org.ex.repositories.TaskDao;
import org.ex.repositories.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component("TaskServiceImpl")
public class TaskServiceImpl implements TaskService{

    private UserDao userDao;
    private TaskDao taskDao;
    private GroupDao groupDao;

    @Autowired
    public TaskServiceImpl(UserDao userDao, TaskDao taskDao, GroupDao groupDao) {
        this.userDao = userDao;
        this.taskDao = taskDao;
        this.groupDao = groupDao;
    }

    /**
     * Gets all the tasks in the dao
     *
     * @return list of tasks
     */
    @Override
    public List<Task> getAllTasks() {
        return this.taskDao.getAllTasks();
    }

    /**
     * Gets a task by its id
     *
     * @param id
     * @return task or null if no task was found
     */
    @Override
    public Task getTaskById(int id) {
        return this.taskDao.getTaskById(id);
    }

    /**
     * Gets all the tasks for a user by userId
     *
     * @param id
     * @return list of tasks
     */
    @Override
    public List<Task> getTasksByUserId(int id) {
        return this.taskDao.getTasksByUserId(id);
    }

    /**
     * Gets all the tasks for a group by groupId
     *
     * @param id
     * @return list of tasks
     */
    @Override
    public List<Task> getTasksByGroupId(int id) {
        return this.taskDao.getTasksByGroupId(id);
    }

    /**
     * Creates a new task
     *
     * @param task
     * @return boolean if task was created
     */
    @Override
    public boolean createTask(Task task) {
        if(task.getUser_id() != null) {
            int userId = task.getUser_id();
            if(this.getUserById(userId) != null) {
                return persistUserTask(task);
            }
        } else if(task.getGroup_id() != null) {
            int groupId = task.getGroup_id();
            if(this.getGroupById(groupId) != null) {
                return persistGroupTask(task);
            }
        }
        return false;
    }

    /**
     * Updates an existing task with new information
     *
     * @param task
     * @return boolean if task was updated
     */
    @Override
    public boolean updateTask(Task task) {
        if(task.getGroup_id() != null || task.getUser_id() != null) {
            try {
                int id = task.getId();
                String taskName = task.getTask_name();
                String description = task.getDescription();
                Timestamp startDate = task.getStart_date();
                Timestamp endDate = task.getEnd_date();
                String status = task.getStatus().toString();
                int minWorked = task.getMinutes_worked();
                this.taskDao.updateTask(id, taskName, description, startDate, endDate, status, minWorked);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * Delete a task by taskId
     *
     * @param id
     * @return boolean if task was deleted
     */
    @Override
    public boolean deleteTask(int id) {
        try {
            this.taskDao.deleteTask(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean persistUserTask(Task task) {
        try{
            int userId = task.getUser_id();
            String taskName = task.getTask_name();
            String description = task.getDescription();
            Timestamp startDate = task.getStart_date();
            Timestamp endDate = task.getEnd_date();
            this.taskDao.insertNewUserTask(userId, taskName, description, startDate, endDate);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean persistGroupTask(Task task) {
        try {
            int groupId = task.getGroup_id();
            String taskName = task.getTask_name();
            String description = task.getDescription();
            Timestamp startDate = task.getStart_date();
            Timestamp endDate = task.getEnd_date();
            this.taskDao.insertNewGroupTask(groupId, taskName, description, startDate, endDate);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Group getGroupById(int id) {
        return this.groupDao.getGroupId(id);
    }

    private User getUserById(int id) {
        return this.userDao.getUserById(id);
    }
}
