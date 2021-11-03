package org.ex.controllers;

import org.ex.models.dto.LoginRequest;
import org.ex.models.dto.SessionUser;
import org.ex.models.User;
import org.ex.models.dto.UpdateUser;
import org.ex.services.UserService;
import org.ex.services.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "user")
@CrossOrigin(origins = "${angular.url}")
public class UserController {

    private final Logger LOGGER= LoggerFactory.getLogger(this.getClass());
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/group/{id}")
    public ResponseEntity<List<SessionUser>> getAllUsersByGroup(@PathVariable int id) {
        System.out.println("Hello? this working?");
        List<SessionUser> users = this.userService.getUsersByGroup(id);

        if(users != null) {
            if(users.size() > 0) {
                LOGGER.debug("Returning list of users");
                return ResponseEntity.ok().body(users);
            }
        }

        LOGGER.debug("Failed to find any users, status not found");
        return ResponseEntity.notFound().build();
    }

    @PostMapping(path = "/register")
    @Transactional
    public ResponseEntity createUser(@RequestBody User user) {
        System.out.println(user.getUser_type());
        boolean result = this.userService.registerNewUser(user);

        if(result) {
            LOGGER.debug("Created new user " + user);
            return ResponseEntity.status(201).build();
        } else {
            LOGGER.debug("Failed to create new user, status conflict");
            return ResponseEntity.status(409).build();
        }
    }

    @PostMapping(path = "/login")
    public ResponseEntity<SessionUser> login(@RequestBody LoginRequest loginRequest) {
        User user = this.userService.getUserByUserName(loginRequest.getUser_name());

        SessionUser resultUser = this.userService.validateUser(user, loginRequest);

        if(resultUser != null) {
            LOGGER.debug(loginRequest.getUser_name() + " successfully logged in");
            return ResponseEntity.accepted().body(resultUser);
        }

        LOGGER.debug("user " + loginRequest.getUser_name() + " failed login");
        return ResponseEntity.status(403).build();
    }

    @PutMapping(path = "/update")
    @Transactional
    public ResponseEntity updateUser(@RequestBody UpdateUser user) {

        boolean result = this.userService.updateUser(user);

        User u = this.userService.getUserById(user.getId());

        if(result && u != null) {
            LOGGER.debug("Successfully updated " + user.getUser_name() + " with new information");
            return ResponseEntity.ok().build();
        }

        LOGGER.debug("Failed to update user: " + user.getUser_name());
        return ResponseEntity.badRequest().build();
    }
}
