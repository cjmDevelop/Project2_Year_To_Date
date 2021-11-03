package serviceTests;

import org.ex.models.Group;
import org.ex.models.User;
import org.ex.models.UserType;
import org.ex.models.dto.UserGroup;
import org.ex.models.dto.UserGroupName;
import org.ex.repositories.GroupDao;
import org.ex.repositories.UserDao;
import org.ex.services.GroupServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {GroupServiceImpl.class})
@ExtendWith(SpringExtension.class)
class GroupServiceImplTest {
    @MockBean
    private GroupDao groupDao;

    @Autowired
    private GroupServiceImpl groupServiceImpl;

    @MockBean
    private UserDao userDao;

    @Test
    void testConstructor() {
        assertTrue((new GroupServiceImpl(mock(GroupDao.class), mock(UserDao.class))).getAllGroups().isEmpty());
    }

    @Test
    void testGetAllGroups() {
        ArrayList<Group> groupList = new ArrayList<Group>(); //groupList of type Group in ArrayList
        when(this.groupDao.getAllGroups()).thenReturn(groupList);
        List<Group> actualAllGroups = this.groupServiceImpl.getAllGroups(); //actualAllGroups of type Group in List
        assertSame(groupList, actualAllGroups); // Asserts that two objects refer to the same object of type Group
        //assertTrue(actualAllGroups.isEmpty());
        verify(this.groupDao).getAllGroups(); //verifying getAllGroups method
    }

    @Test
    void testCreateGroup() {
       // doNothing().when(this.groupDao).insertGroup((String) any(), (String) any(), anyInt());

        Group group = new Group();
        group.setId(1);
        group.setDescription("A cool group, doing cool things");
        group.setGroup_name("Coolness");
        group.setCreated_by(1);
        assertTrue(this.groupServiceImpl.createGroup(group));
        verify(this.groupDao).insertGroup(group.getGroup_name(), group.getDescription(), group.getId()); //verify groupname = Coolness ect.
        //assertTrue(this.groupServiceImpl.getAllGroups().isEmpty());
    }

    @Test
    void testAddUserToGroup() {
        assertTrue(this.groupServiceImpl.addUserToGroup(new UserGroup(1, 1, 1))); //assert addUserToGroup method is working by passing in id's
        verify(this.groupDao).userIntoGroup(1, 1); //verify groupDao is working by passing in appropriate id values
    }

//    @Test
//    void testAddUserToGroup2() {
//        doNothing().when(this.groupDao).userIntoGroup(anyInt(), anyInt());
//        assertFalse(this.groupServiceImpl.addUserToGroup(null));
//    }

    @Test
    void testAddUserToGroup2() {
        UserGroup userGroup = mock(UserGroup.class); //using mockito to create a UserGroup userGroup
        when(userGroup.getUser_id()).thenReturn(1);
        when(userGroup.getGroup_id()).thenReturn(1);
        assertTrue(this.groupServiceImpl.addUserToGroup(userGroup)); //assert addUserToGroup method is working by setting assert to true
        verify(this.groupDao).userIntoGroup(1, 1); // verifying groupDao.userIntoGroup by id's of 1 that were return earlier in the method
        verify(userGroup).getGroup_id(); //verify groupId is 1
        verify(userGroup).getUser_id(); //verify userId is 1
    }

    @Test
    void testAddUserToGroupByNames() {
        User user = new User();
        user.setEmail("liu.Kang@example.org");
        user.setUser_password("Mortal-Kombat");
        user.setUser_type(UserType.MANAGER);
        user.setId(1);
        user.setLast_name("Kang");
        user.setFirst_name("Liu");
        user.setUser_name("lKang");
        when(this.userDao.getUserByUserName("lKang")).thenReturn(user); //checking to return user by username
        when(this.groupDao.getGroupsByUser(1)).thenReturn(new ArrayList<Group>()); // checking to get group by userID
        assertFalse(this.groupServiceImpl.addUserToGroupByNames(new UserGroupName("lKang", "KombatGroup"))); //asserting false because user already exist to group
        verify(this.userDao).getUserByUserName("lKang"); //works by passing in correct username
        verify(this.groupDao).getGroupsByUser(1);//works by passing in correct id
      //  assertTrue(this.groupServiceImpl.getAllGroups().isEmpty());
    }

    @Test
    void testAddUserToGroupByNames2() {
        User user = new User();
        user.setEmail("helen.hunter@example.org");
        user.setUser_password("iloveyou");
        user.setUser_type(UserType.MANAGER);
        user.setId(1);
        user.setLast_name("Hunt");
        user.setFirst_name("Helen");
        user.setUser_name("hunter");
        when(this.userDao.getUserByUserName("hunter")).thenReturn(user);

        Group group = new Group();
        group.setId(1);
        group.setDescription("The Cast Away movie group");
        group.setGroup_name("CastAway");
        group.setCreated_by(0);

        ArrayList<Group> groupList = new ArrayList<Group>();
        groupList.add(group);
        when(this.groupDao.getGroupsByUser(1)).thenReturn(groupList);
        assertFalse(this.groupServiceImpl.addUserToGroupByNames(new UserGroupName("hunter", "CastAway")));
        verify(this.userDao).getUserByUserName("hunter");
        verify(this.groupDao).getGroupsByUser(1);
        assertTrue(this.groupServiceImpl.getAllGroups().isEmpty());
    }

//    @Test
//    void testAddUserToGroupByNames3() {
//        User user = new User();
//        user.setEmail("tom.hanks@example.org");
//        user.setUser_password("12345");
//        user.setUser_type(UserType.MANAGER);
//        user.setId(1);
//        user.setLast_name("Hanks");
//        user.setFirst_name("Tom");
//        user.setUser_name("HankyPanky");
//        when(this.userDao.getUserByUserName("HankyPanky")).thenReturn(user);
//
//        Group group = new Group();
//        group.setId(1);
//        group.setDescription("The Cast Away movie group");
//        group.setGroup_name("CastAway");
//        group.setCreated_by(0);
//
//        ArrayList<Group> groupList = new ArrayList<Group>();
//        groupList.add(group);
//
//        when(this.groupDao.getGroupsByUser(1)).thenReturn(groupList);
//       // assertFalse(this.groupServiceImpl.addUserToGroupByNames(new UserGroupName("HankyPanky", "CastAway")));
//        verify(this.userDao).getUserByUserName("HankyPanky");
//        verify(this.groupDao).getGroupsByUser(1);
//        verify(this.groupDao).userIntoGroupByNames("HankyPanky", "CastAway");
//        //assertTrue(this.groupServiceImpl.getAllGroups().isEmpty());
//    }

    @Test
    void testGetAllGroupsByUser() {
        ArrayList<Group> groupList = new ArrayList<Group>();
        when(this.groupDao.getGroupsByUser(anyInt())).thenReturn(groupList);
        List<Group> actualAllGroupsByUser = this.groupServiceImpl.getAllGroupsByUser(123);
        assertSame(groupList, actualAllGroupsByUser);
        assertTrue(actualAllGroupsByUser.isEmpty());
        verify(this.groupDao).getGroupsByUser(anyInt());
        assertTrue(this.groupServiceImpl.getAllGroups().isEmpty());
    }

    @Test
    void testDeleteGroup() {
        Group group = new Group();
        group.setId(1);
        group.setDescription("The characteristics of someone or something");
        group.setGroup_name("Group name");
        group.setCreated_by(1);
        doNothing().when(this.groupDao).deleteGroup(1);
        doNothing().when(this.groupDao).deleteUserGroupAssociation(1);
        when(this.groupDao.getGroupId(anyInt())).thenReturn(group);
        assertTrue(this.groupServiceImpl.deleteGroup(123));
        verify(this.groupDao).deleteGroup(anyInt());
        verify(this.groupDao).deleteUserGroupAssociation(anyInt());
        verify(this.groupDao).getGroupId(anyInt());
        assertTrue(this.groupServiceImpl.getAllGroups().isEmpty());
    }

    @Test
    void testUpdateGroup() {
        Group group = new Group();
        group.setId(1);
        group.setDescription("The characteristics of someone or something");
        group.setGroup_name("Group name");
        group.setCreated_by(1);
        doNothing().when(this.groupDao).updateGroup(anyInt(), (String) any(), (String) any());
        when(this.groupDao.getGroupId(anyInt())).thenReturn(group);

        Group group1 = new Group();
        group1.setId(1);
        group1.setDescription("The characteristics of someone or something");
        group1.setGroup_name("Group name");
        group1.setCreated_by(1);
        assertTrue(this.groupServiceImpl.updateGroup(group1));
        verify(this.groupDao).getGroupId(anyInt());
        verify(this.groupDao).updateGroup(anyInt(), (String) any(), (String) any());
        assertTrue(this.groupServiceImpl.getAllGroups().isEmpty());
    }
}

