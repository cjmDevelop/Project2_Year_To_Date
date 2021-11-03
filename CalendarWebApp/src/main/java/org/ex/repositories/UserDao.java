package org.ex.repositories;

import org.ex.models.User;
import org.ex.models.UserType;
import org.ex.models.dto.SessionUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface UserDao extends JpaRepository<User, Long> {

    @Query(value = "select * from t_user where id = :id", nativeQuery = true)
    User getUserById(@Param("id") int id);

    @Query(value = "select * from t_user where user_name = :username", nativeQuery = true)
    User getUserByUserName(@Param("username") String userName);

    @Query(value = "select t_user.id, first_name, last_name, user_name, user_type, email, user_password from t_user\n" +
            "INNER JOIN user_group on user_group.user_id = t_user.id\n" +
            "where user_group.group_id = :id", nativeQuery = true)
    List<User> getUsersByGroup(@Param("id") int groupId);

    @Modifying
    @Query(value = "insert into t_user (first_name, last_name, user_name, user_type, email, user_password)\n" +
            "values (:firstName, :lastName, :userName, :userType, :email, :password)", nativeQuery = true)
    void insertUser(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("userName") String userName, @Param("userType") String type, @Param("email") String email, @Param("password") String password);

    @Modifying
    @Query(value = "update t_user \n" +
            "set first_name = :firstName, last_name = :lastName, user_name = :userName, email = :email, user_password = :password\n" +
            "where id = :id", nativeQuery = true)
    void updateUserWithPass(@Param("id") int id, @Param("firstName") String firstName, @Param("lastName") String lastName, @Param("userName") String userName, @Param("email") String email, @Param("password") String password);

    @Modifying
    @Query(value = "update t_user \n" +
            "set first_name = :firstName, last_name = :lastName, user_name = :userName, email = :email\n" +
            "where id = :id", nativeQuery = true)
    void updateUserNoPass(@Param("id") int id, @Param("firstName") String firstName, @Param("lastName") String lastName, @Param("userName") String userName, @Param("email") String email);
}
