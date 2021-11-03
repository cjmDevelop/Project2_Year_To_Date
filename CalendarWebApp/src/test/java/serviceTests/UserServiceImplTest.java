package serviceTests;

import org.ex.models.User;
import org.ex.models.UserType;
import org.ex.models.dto.LoginRequest;
import org.ex.models.dto.SessionUser;
import org.ex.models.dto.UpdateUser;
import org.ex.repositories.UserDao;
import org.ex.services.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
    @MockBean
    private UserDao userDao;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Test
    void testConstructor() {
        new UserServiceImpl(mock(UserDao.class));
    }

    @Test
    void testGetUserById() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setUser_password("iloveyou");
        user.setUser_type(UserType.MANAGER);
        user.setId(1);
        user.setLast_name("Doe");
        user.setFirst_name("Jane");
        user.setUser_name("User name");
        when(this.userDao.getUserById(anyInt())).thenReturn(user);
        assertSame(user, this.userServiceImpl.getUserById(1));
        verify(this.userDao).getUserById(anyInt());
    }

    @Test
    void testGetUsersByGroup() {
        when(this.userDao.getUsersByGroup(anyInt())).thenReturn(new ArrayList<User>());
        assertTrue(this.userServiceImpl.getUsersByGroup(123).isEmpty());
        verify(this.userDao).getUsersByGroup(anyInt());
    }

    @Test
    void testGetUsersByGroup2() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setUser_password("iloveyou");
        user.setUser_type(UserType.MANAGER);
        user.setId(1);
        user.setLast_name("Doe");
        user.setFirst_name("Jane");
        user.setUser_name("User name");

        ArrayList<User> userList = new ArrayList<User>();
        userList.add(user);
        when(this.userDao.getUsersByGroup(anyInt())).thenReturn(userList);
        assertEquals(1, this.userServiceImpl.getUsersByGroup(123).size());
        verify(this.userDao).getUsersByGroup(anyInt());
    }

    @Test
    void testGetUsersByGroup3() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setUser_password("iloveyou");
        user.setUser_type(UserType.MANAGER);
        user.setId(1);
        user.setLast_name("Doe");
        user.setFirst_name("Jane");
        user.setUser_name("User name");

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setUser_password("iloveyou");
        user1.setUser_type(UserType.MANAGER);
        user1.setId(1);
        user1.setLast_name("Doe");
        user1.setFirst_name("Jane");
        user1.setUser_name("User name");

        ArrayList<User> userList = new ArrayList<User>();
        userList.add(user1);
        userList.add(user);
        when(this.userDao.getUsersByGroup(anyInt())).thenReturn(userList);
        assertEquals(2, this.userServiceImpl.getUsersByGroup(123).size());
        verify(this.userDao).getUsersByGroup(anyInt());
    }

    @Test
    void testRegisterNewUser() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setUser_password("iloveyou");
        user.setUser_type(UserType.MANAGER);
        user.setId(1);
        user.setLast_name("Doe");
        user.setFirst_name("Jane");
        user.setUser_name("User name");
        when(this.userDao.getUserByUserName((String) any())).thenReturn(user);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setUser_password("iloveyou");
        user1.setUser_type(UserType.MANAGER);
        user1.setId(1);
        user1.setLast_name("Doe");
        user1.setFirst_name("Jane");
        user1.setUser_name("User name");
        assertFalse(this.userServiceImpl.registerNewUser(user1));
        verify(this.userDao).getUserByUserName((String) any());
    }

    @Test
    void testGetUserByUserName() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setUser_password("iloveyou");
        user.setUser_type(UserType.MANAGER);
        user.setId(1);
        user.setLast_name("Doe");
        user.setFirst_name("Jane");
        user.setUser_name("User name");
        when(this.userDao.getUserByUserName((String) any())).thenReturn(user);
        assertSame(user, this.userServiceImpl.getUserByUserName("janedoe"));
        verify(this.userDao).getUserByUserName((String) any());
    }

    @Test
    void testUpdateUser() {
        doNothing().when(this.userDao)
                .updateUserWithPass(anyInt(), (String) any(), (String) any(), (String) any(), (String) any(), (String) any());
        assertTrue(this.userServiceImpl
                .updateUser(new UpdateUser(1, "Jane", "Doe", "User name", "jane.doe@example.org", "iloveyou")));
        verify(this.userDao).updateUserWithPass(anyInt(), (String) any(), (String) any(), (String) any(), (String) any(),
                (String) any());
    }

    @Test
    void testUpdateUser2() {
        doNothing().when(this.userDao)
                .updateUserNoPass(anyInt(), (String) any(), (String) any(), (String) any(), (String) any());
        doNothing().when(this.userDao)
                .updateUserWithPass(anyInt(), (String) any(), (String) any(), (String) any(), (String) any(), (String) any());
        assertTrue(
                this.userServiceImpl.updateUser(new UpdateUser(1, "Jane", "Doe", "User name", "jane.doe@example.org", null)));
        verify(this.userDao).updateUserNoPass(anyInt(), (String) any(), (String) any(), (String) any(), (String) any());
    }

    @Test
    void testUpdateUser3() {
        doNothing().when(this.userDao)
                .updateUserNoPass(anyInt(), (String) any(), (String) any(), (String) any(), (String) any());
        doNothing().when(this.userDao)
                .updateUserWithPass(anyInt(), (String) any(), (String) any(), (String) any(), (String) any(), (String) any());
        assertTrue(this.userServiceImpl.updateUser(new UpdateUser()));
        verify(this.userDao).updateUserNoPass(anyInt(), (String) any(), (String) any(), (String) any(), (String) any());
    }

    @Test
    void testValidateUser() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setUser_password("iloveyou");
        user.setUser_type(UserType.MANAGER);
        user.setId(1);
        user.setLast_name("Doe");
        user.setFirst_name("Jane");
        user.setUser_name("User name");
        SessionUser actualValidateUserResult = this.userServiceImpl.validateUser(user,
                new LoginRequest("User name", "iloveyou"));
        assertEquals("jane.doe@example.org", actualValidateUserResult.getEmail());
        assertEquals(UserType.MANAGER, actualValidateUserResult.getUser_type());
        assertEquals("User name", actualValidateUserResult.getUser_name());
        assertEquals("Doe", actualValidateUserResult.getLast_name());
        assertEquals(1, actualValidateUserResult.getId());
        assertEquals("Jane", actualValidateUserResult.getFirst_name());
    }

    @Test
    void testValidateUser2() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setUser_password("User password");
        user.setUser_type(UserType.MANAGER);
        user.setId(1);
        user.setLast_name("Doe");
        user.setFirst_name("Jane");
        user.setUser_name("User name");
        LoginRequest loginRequest = mock(LoginRequest.class);
        when(loginRequest.getPassword()).thenReturn("iloveyou");
        assertNull(this.userServiceImpl.validateUser(user, loginRequest));
        verify(loginRequest).getPassword();
    }
}

