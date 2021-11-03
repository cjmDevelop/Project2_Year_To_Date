package org.ex.controllers;

import org.ex.models.Group;
import org.ex.models.dto.UserGroup;
import org.ex.models.dto.UserGroupName;
import org.ex.services.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "group")
@CrossOrigin(origins = "${angular.url}")
public class GroupController {

    private final Logger LOGGER= LoggerFactory.getLogger(this.getClass());
    private GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<Group>> getAllGroups() {
        List<Group> groups = this.groupService.getAllGroups();

        if(groups != null) {
            if(groups.size() > 0) {
                LOGGER.debug("Returning list of all groups");
                return ResponseEntity.ok().body(groups);
            }
        }

        LOGGER.debug("Failed to find any groups");
        return ResponseEntity.notFound().build();
    }

    @PostMapping(path = "/new")
    @Transactional
    public ResponseEntity createGroup(@RequestBody Group group) {
        System.out.println(group);

        boolean status = this.groupService.createGroup(group);

        if(status) {
            LOGGER.debug("Successfully created new group " + group.getGroup_name());
            return ResponseEntity.ok().build();
        }

        LOGGER.debug("Failed to create group " + group);
        return ResponseEntity.badRequest().build();
    }

    @PostMapping(path = "/user/id")
    @Transactional
    public ResponseEntity addUserToGroup(@RequestBody UserGroup userGroup) {

        boolean status = this.groupService.addUserToGroup(userGroup);

        if(status) {
            LOGGER.debug("Successfully added user_id: " + userGroup.getUser_id() + " to group_id: " + userGroup.getGroup_id());
            return ResponseEntity.ok().build();
        }

        LOGGER.debug("Failed to add user_id: " + userGroup.getUser_id() + " to group_id: " + userGroup.getGroup_id());
        return ResponseEntity.badRequest().build();
    }

    @PostMapping(path = "/user")
    @Transactional
    public ResponseEntity addUserToGroup(@RequestBody UserGroupName names) {

        boolean status = this.groupService.addUserToGroupByNames(names);

        if(status) {
            LOGGER.debug("Successfully added " + names.getUser_name() + " to " + names.getGroup_name());
            return ResponseEntity.ok().build();
        }

        LOGGER.debug("Failed to add " + names.getUser_name() + " to " + names.getGroup_name());
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(path = "/user/{id}")
    public ResponseEntity<List<Group>> getUsersGroups(@PathVariable int id) {
        List<Group> groups = this.groupService.getAllGroupsByUser(id);

        if(groups != null) {
            if(groups.size() > 0) {
                LOGGER.debug("Returning list of groups that user_id: " + id + " is a part of");
                return ResponseEntity.ok().body(groups);
            }
        }

        LOGGER.debug("Failed to find any groups for user with id: " + id);
        return ResponseEntity.notFound().build();
    }

    @PutMapping(path = "/update")
    @Transactional
    public ResponseEntity updateGroup(@RequestBody Group group) {

        boolean status = this.groupService.updateGroup(group);

        if(status) {
            LOGGER.debug("Successfully updated group information");
            return ResponseEntity.ok().build();
        }

        LOGGER.debug("Failed to update group information " + group);
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping(path = "/delete/{id}")
    @Transactional()
    public ResponseEntity deleteGroup(@PathVariable int id) {

        boolean status = this.groupService.deleteGroup(id);

        if(status) {
            LOGGER.debug("Deleted group with id: " + id);
            return ResponseEntity.ok().build();
        }

        LOGGER.debug("Failed to delete group with id: " + id + " group not found");
        return ResponseEntity.status(409).build();
    }

}
