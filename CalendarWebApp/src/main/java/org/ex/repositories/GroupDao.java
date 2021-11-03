package org.ex.repositories;

import org.ex.models.Group;
import org.ex.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupDao extends JpaRepository<Group, Long> {

    @Query(value = "select * from t_group where id = :id", nativeQuery = true)
    Group getGroupId(@Param("id") int id);

    @Query(value = "select * from t_group where group_name = :name", nativeQuery = true)
    Group getGroupByName(@Param("name") String name);

    @Modifying
    @Query(value = "INSERT INTO t_group (group_name, description, created_by)\n" +
            "VALUES (:name, :description, :id)", nativeQuery = true)
    void insertGroup(@Param("name") String name, @Param("description") String description, @Param("id") int userId);

    //Many-to-many table
    @Modifying
    @Query(value = "INSERT INTO user_group (group_id, user_id)\n" +
            "VALUES (:groupId, :userId)", nativeQuery = true)
    void userIntoGroup(@Param("groupId") int groupId, @Param("userId") int userId);

    @Modifying
    @Query(value = "insert into user_group (user_id, group_id)\n" +
            "values ((select id from t_user where user_name = :userName), (select id from t_group where group_name = :groupName))", nativeQuery = true)
    void userIntoGroupByNames(@Param("userName") String userName, @Param("groupName") String groupName);

    @Query(value = "DELETE FROM user_group WHERE group_id = :id", nativeQuery = true)
    @Modifying
    void deleteUserGroupAssociation(@Param("id") int id);

    @Query(value = "select * from t_group", nativeQuery = true)
    List<Group> getAllGroups();

    @Query(value = "select t_group.id, group_name, description, created_by from t_group\n" +
            "INNER join user_group on user_group.group_id = t_group.id\n" +
            "where user_group.user_id = :id", nativeQuery = true)
    List<Group> getGroupsByUser(@Param("id") int id); //select t_group.id, group_name, description, created_by from t_group

    @Query(value = "update t_group \n" +
            "set group_name = :name, description = :description\n" +
            "where id = :id", nativeQuery = true)
    @Modifying
    void updateGroup(@Param("id") int id, @Param("name") String groupName, @Param("description") String description);

    @Query(value = "DELETE FROM t_group WHERE id = :id", nativeQuery = true)
    @Modifying
    void deleteGroup(@Param("id") int id);
}
