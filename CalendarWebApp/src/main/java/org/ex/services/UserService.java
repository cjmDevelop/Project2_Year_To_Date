package org.ex.services;

import org.ex.models.User;
import org.ex.models.dto.LoginRequest;
import org.ex.models.dto.SessionUser;
import org.ex.models.dto.UpdateUser;

import java.util.List;

public interface UserService {

    User getUserById(int id);

    List<SessionUser> getUsersByGroup(int groupId);

    boolean registerNewUser(User user);

    User getUserByUserName(String username);

    boolean updateUser(UpdateUser user);

    SessionUser validateUser(User user, LoginRequest loginRequest);
}
