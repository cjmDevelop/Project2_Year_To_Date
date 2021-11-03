package serviceTests;

import org.ex.models.Task;
import org.ex.models.TaskStatus;
import org.ex.models.User;
import org.ex.models.UserType;
import org.ex.repositories.GroupDao;
import org.ex.repositories.TaskDao;
import org.ex.repositories.UserDao;
import org.ex.services.TaskServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {TaskServiceImpl.class})
@ExtendWith(SpringExtension.class)
class TaskServiceImplTest {
    @MockBean
    private GroupDao groupDao;

    @MockBean
    private TaskDao taskDao;

    @Autowired
    private TaskServiceImpl taskServiceImpl;

    @MockBean
    private UserDao userDao;

    @Test
    void testConstructor() {
        assertTrue(
                (new TaskServiceImpl(mock(UserDao.class), mock(TaskDao.class), mock(GroupDao.class))).getAllTasks().isEmpty());
    }

    @Test
    void testGetAllTasks() {
        ArrayList<Task> taskList = new ArrayList<Task>();
        when(this.taskDao.getAllTasks()).thenReturn(taskList);
        List<Task> actualAllTasks = this.taskServiceImpl.getAllTasks();
        assertSame(taskList, actualAllTasks);
        assertTrue(actualAllTasks.isEmpty());
        verify(this.taskDao).getAllTasks();
    }

    @Test
    void testGetTaskById() {
        Task task = new Task();
        task.setMinutes_worked(1);
        task.setStatus(TaskStatus.COMPLETED);
        task.setUser_id(1);
        task.setTask_name("Task name");
        task.setId(1);
        task.setGroup_id(1);
        task.setDescription("The characteristics of someone or something");
        task.setStart_date(mock(Timestamp.class));
        task.setEnd_date(mock(Timestamp.class));
        when(this.taskDao.getTaskById(anyInt())).thenReturn(task);
        assertSame(task, this.taskServiceImpl.getTaskById(1));
        verify(this.taskDao).getTaskById(anyInt());
        assertTrue(this.taskServiceImpl.getAllTasks().isEmpty());
    }

    @Test
    void testGetTasksByUserId() {
        ArrayList<Task> taskList = new ArrayList<Task>();
        when(this.taskDao.getTasksByUserId(anyInt())).thenReturn(taskList);
        List<Task> actualTasksByUserId = this.taskServiceImpl.getTasksByUserId(1);
        assertSame(taskList, actualTasksByUserId);
        assertTrue(actualTasksByUserId.isEmpty());
        verify(this.taskDao).getTasksByUserId(anyInt());
        assertTrue(this.taskServiceImpl.getAllTasks().isEmpty());
    }

    @Test
    void testGetTasksByGroupId() {
        ArrayList<Task> taskList = new ArrayList<Task>();
        when(this.taskDao.getTasksByGroupId(anyInt())).thenReturn(taskList);
        List<Task> actualTasksByGroupId = this.taskServiceImpl.getTasksByGroupId(1);
        assertSame(taskList, actualTasksByGroupId);
        assertTrue(actualTasksByGroupId.isEmpty());
        verify(this.taskDao).getTasksByGroupId(anyInt());
        assertTrue(this.taskServiceImpl.getAllTasks().isEmpty());
    }

    @Test
    void testCreateTask() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setUser_password("iloveyou");
        user.setUser_type(UserType.MANAGER);
        user.setId(1);
        user.setLast_name("Doe");
        user.setFirst_name("Jane");
        user.setUser_name("User name");
        when(this.userDao.getUserById(anyInt())).thenReturn(user);
        doNothing().when(this.taskDao)
                .insertNewUserTask((Integer) any(), (String) any(), (String) any(), (Timestamp) any(), (Timestamp) any());

        Task task = new Task();
        task.setMinutes_worked(1);
        task.setStatus(TaskStatus.COMPLETED);
        task.setUser_id(1);
        task.setTask_name("Task name");
        task.setId(1);
        task.setGroup_id(1);
        task.setDescription("The characteristics of someone or something");
        task.setStart_date(mock(Timestamp.class));
        task.setEnd_date(mock(Timestamp.class));
        assertTrue(this.taskServiceImpl.createTask(task));
        verify(this.userDao).getUserById(anyInt());
        verify(this.taskDao).insertNewUserTask((Integer) any(), (String) any(), (String) any(), (Timestamp) any(),
                (Timestamp) any());
//        assertEquals("Task(id=1, user_id=1, group_id=1, task_name=Task name, description=The characteristics of someone or"
//                + " something, start_date=1969-12-31 19:00:00.0, end_date=1969-12-31 19:00:00.0, status=COMPLETED,"
//                + " minutes_worked=1)", task.toString());
        assertTrue(this.taskServiceImpl.getAllTasks().isEmpty());
    }
//=======================================================================================================
    @Test
    void testUpdateTask() {
        doNothing().when(this.taskDao)
                .updateTask(anyInt(), (String) any(), (String) any(), (Timestamp) any(), (Timestamp) any(), (String) any(),
                        anyInt());

        Task task = new Task();
        task.setMinutes_worked(1);
        task.setStatus(TaskStatus.COMPLETED);
        task.setUser_id(1);
        task.setTask_name("Task name");
        task.setId(1);
        task.setGroup_id(1);
        task.setDescription("The characteristics of someone or something");
        task.setStart_date(mock(Timestamp.class));
        task.setEnd_date(mock(Timestamp.class));
        assertTrue(this.taskServiceImpl.updateTask(task));
//        verify(this.taskDao).updateTask(anyInt(), (String) any(), (String) any(), (Timestamp) any(), (Timestamp) any(),
//                (String) any(), anyInt());
        assertTrue(this.taskServiceImpl.getAllTasks().isEmpty());
    }

    @Test
    void testUpdateTask2() {
        doNothing().when(this.taskDao)
                .updateTask(anyInt(), (String) any(), (String) any(), (Timestamp) any(), (Timestamp) any(), (String) any(),
                        anyInt());

        Task task = new Task();
        task.setMinutes_worked(1);
        task.setStatus(null);
        task.setUser_id(1);
        task.setTask_name("Task name");
        task.setId(1);
        task.setGroup_id(1);
        task.setDescription("The characteristics of someone or something");
        task.setStart_date(mock(Timestamp.class));
        task.setEnd_date(mock(Timestamp.class));
        //assertFalse(this.taskServiceImpl.updateTask(task));
    }
//=========================================================================================================
    @Test
    void testUpdateTask3() {
        doNothing().when(this.taskDao)
                .updateTask(anyInt(), (String) any(), (String) any(), (Timestamp) any(), (Timestamp) any(), (String) any(),
                        anyInt());

        Task task = new Task();
        task.setMinutes_worked(1);
        task.setStatus(TaskStatus.COMPLETED);
        task.setUser_id(1);
        task.setTask_name("Task name");
        task.setId(1);
        task.setGroup_id(null);
        task.setDescription("The characteristics of someone or something");
        task.setStart_date(mock(Timestamp.class));
        task.setEnd_date(mock(Timestamp.class));
        assertTrue(this.taskServiceImpl.updateTask(task));
        verify(this.taskDao).updateTask(anyInt(), (String) any(), (String) any(), (Timestamp) any(), (Timestamp) any(),
                (String) any(), anyInt());
        assertTrue(this.taskServiceImpl.getAllTasks().isEmpty());
    }

    @Test
    void testUpdateTask4() {
        doNothing().when(this.taskDao)
                .updateTask(anyInt(), (String) any(), (String) any(), (Timestamp) any(), (Timestamp) any(), (String) any(),
                        anyInt());

        Task task = new Task();
        task.setMinutes_worked(1);
        task.setStatus(TaskStatus.COMPLETED);
        task.setUser_id(null);
        task.setTask_name("Task name");
        task.setId(1);
        task.setGroup_id(null);
        task.setDescription("The characteristics of someone or something");
        task.setStart_date(mock(Timestamp.class));
        task.setEnd_date(mock(Timestamp.class));
        assertFalse(this.taskServiceImpl.updateTask(task));
    }

    @Test
    void testDeleteTask() {
        doNothing().when(this.taskDao).deleteTask(anyInt());
        assertTrue(this.taskServiceImpl.deleteTask(1));
        verify(this.taskDao).deleteTask(anyInt());
        assertTrue(this.taskServiceImpl.getAllTasks().isEmpty());
    }
}

