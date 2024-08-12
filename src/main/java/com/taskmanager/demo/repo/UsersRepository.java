package com.taskmanager.demo.repo;

import com.taskmanager.demo.db.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Long> {
    @Query(value = "select users_id from Users where first_name like ?1", nativeQuery = true)
    List<Long> getAllUserIdWithUserName(String userName);

    @Query(value = "SELECT users_id FROM Users ORDER BY first_name ASC", nativeQuery = true)
    List<Long> sortByUserName();
}
