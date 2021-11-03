package org.ex.services;

import org.ex.models.User;
import org.ex.models.dto.LoginRequest;
import org.ex.models.dto.SessionUser;
import org.ex.models.dto.UpdateUser;
import org.ex.repositories.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("UserServiceImpl")
public class UserServiceImpl implements UserService{

    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Gets a user by userId
     *
     * @param id
     * @return user
     */
    @Override
    public User getUserById(int id) {
        return this.userDao.getUserById(id);
    }

    /**
     * Gets all users within a group by groupId
     *
     * @param groupId
     * @return list of users, without password
     */
    @Override
    public List<SessionUser> getUsersByGroup(int groupId) {
        List<User> unsafeUsers = this.userDao.getUsersByGroup(groupId);
        List<SessionUser> safeUsers = unsafeUsers.stream().map(uu -> new SessionUser(uu)).collect(Collectors.toList());
        return safeUsers;
    }

    /**
     * Attempts to register a new user
     *
     * @param user
     * @return boolean if the user was registered
     */
    @Override
    public boolean registerNewUser(User user) {
        User queryUser = this.userDao.getUserByUserName(user.getUser_name());

        if(queryUser == null) {
            final String firstName = user.getFirst_name();
            final String lastName = user.getLast_name();
            final String userName = user.getUser_name();
            final String type = user.getUser_type().toString();
            final String email = user.getEmail();
            final String password = user.getUser_password();
            userDao.insertUser(firstName, lastName, userName, type, email, password);
            return true;
        }
        return false;
    }

    /**
     * Gets a user by the username
     *
     * @param username
     * @return user
     */
    @Override
    public User getUserByUserName(String username) {
        return this.userDao.getUserByUserName(username);
    }

    /**
     * Updates an existing user with new information
     *
     * @param user
     * @return boolean if the user was updated
     */
    @Override
    public boolean updateUser(UpdateUser user) {
        int id = user.getId();
        String firstName = user.getFirst_name();
        String lastName = user.getLast_name();
        String userName = user.getUser_name();
        String email = user.getEmail();
        String password = user.getUser_password();

        try {
            if(password != null) {
                this.userDao.updateUserWithPass(id, firstName, lastName, userName, email, password);
            } else {
                this.userDao.updateUserNoPass(id, firstName, lastName, userName, email);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Checks if the login-request credentials match the users
     *
     * @param user
     * @param loginRequest
     * @return a valid sessionUser
     */
    @Override
    public SessionUser validateUser(User user, LoginRequest loginRequest) {
        if(user != null){
            if(user.getUser_password().equals(loginRequest.getPassword())) {
                SessionUser sessionUser = new SessionUser();
                sessionUser.setId(user.getId());
                sessionUser.setUser_name(user.getUser_name());
                sessionUser.setFirst_name(user.getFirst_name());
                sessionUser.setLast_name(user.getLast_name());
                sessionUser.setUser_type(user.getUser_type());
                sessionUser.setEmail(user.getEmail());
                return sessionUser;
            }
        }

        return null;
    }
}
