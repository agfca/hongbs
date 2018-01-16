package com.llrj.hongbs.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.name=:name and u.pwd=:pwd")
    User login(@Param("name") String name,@Param("pwd")String pwd);

    @Query("select u from User u where u.name=:name")
    User findUserByName(@Param("name") String name);

    @Query("select u from User u where u.level=:level")
    List<User> findUserByLevel(@Param("level") String level);
     
    @Query("select u from User u where u.id=:id")
    User findUserById(@Param("id") Integer id);

    @Query("update User u  set u.pwd=:pwd where u.id=:id")
    int updateUserPwd(@Param("id") Integer id,@Param("pwd") String pwd);

    @Query("delete from User  u where 1=1 and u.id=:id")
    @Modifying
    int deleteById(@Param("id") Integer id);
}
