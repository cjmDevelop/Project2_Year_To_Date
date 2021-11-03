package org.ex.services;

import org.ex.models.Group;
import org.ex.models.User;
import org.ex.models.dto.UserGroup;
import org.ex.models.dto.UserGroupName;
import org.ex.repositories.GroupDao;
import org.ex.repositories.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("GroupServiceImpl")
public class GroupServiceImpl implements GroupService{

    private GroupDao groupDao;
    private UserDao userDao;

    @Autowired
    public GroupServiceImpl(GroupDao groupDao, UserDao userDao) {
        this.groupDao = groupDao;
        this.userDao = userDao;
    }

    /**
     * Gets all the groups from the injected group dao
     *
     * @return list of all the groups
     */
    @Override
    public List<Group> getAllGroups() {
        return this.groupDao.getAllGroups();
    }


    /**
     * Create a new group in the repository
     *
     * @param group
     * @return boolean if the group was successfully created
     */
    @Override
    public boolean createGroup(Group group) {
        try {
            String name = group.getGroup_name();
            String description = group.getDescription();
            int userId = group.getCreated_by();
            this.groupDao.insertGroup(name, description, userId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Adds a user to a group by their ids
     *
     * @param userGroup
     * @return boolean if association table row was created
     */
    @Override
    public boolean addUserToGroup(UserGroup userGroup) {
        try {
            int groupId = userGroup.getGroup_id();
            int userId = userGroup.getUser_id();
            this.groupDao.userIntoGroup(groupId, userId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Adds a user to a group by the username and group-name
     *
     * @param names
     * @return boolean if the user was added
     */
    @Override
    public boolean addUserToGroupByNames(UserGroupName names) {
        String userName = names.getUser_name();
        String groupName = names.getGroup_name();

        User user = this.userDao.getUserByUserName(userName);
        if(user != null) {
            List<Group> userGroups = this.groupDao.getGroupsByUser(user.getId());

            if(userGroups != null) {
                if(userGroups.size() > 0) {
                    for (Group group : userGroups) {
                        if (group.getGroup_name().equals(groupName)) {
                            return false;
                        }
                    }
                }

                try {
                    this.groupDao.userIntoGroupByNames(userName, groupName);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        }

        return false;
    }

    /**
     * Gets all the groups a user is in by the userId
     *
     * @param userId
     * @return list of all the groups a user is in
     */
    @Override
    public List<Group> getAllGroupsByUser(int userId) {
        return this.groupDao.getGroupsByUser(userId);
    }

    /**
     * Deletes a group and all associations by the groupId
     *
     * @param groupId
     * @return boolean if the deletion was successful
     */
    @Override
    public boolean deleteGroup(int groupId) {
        Group g = this.groupDao.getGroupId(groupId);

        if(g != null) {
            try {
                this.groupDao.deleteUserGroupAssociation(groupId);
                this.groupDao.deleteGroup(groupId);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Updates a group with new information
     *
     * @param group
     * @return boolean if the update was successful
     */
    @Override
    public boolean updateGroup(Group group) {
        Group g = this.groupDao.getGroupId(group.getId());

        if(g != null) {
            try {
                int id = group.getId();
                String name = group.getGroup_name();
                String description = group.getDescription();
                this.groupDao.updateGroup(id, name, description);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }
}
