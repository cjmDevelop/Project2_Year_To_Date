package org.ex.services;

import org.ex.models.Group;
import org.ex.models.dto.UserGroup;
import org.ex.models.dto.UserGroupName;

import java.util.List;

public interface GroupService {

    List<Group> getAllGroups();

    boolean createGroup(Group group);

    boolean addUserToGroup(UserGroup userGroup);

    boolean addUserToGroupByNames(UserGroupName names);

    List<Group> getAllGroupsByUser(int userId);

    boolean deleteGroup(int groupId);

    boolean updateGroup(Group group);

}
