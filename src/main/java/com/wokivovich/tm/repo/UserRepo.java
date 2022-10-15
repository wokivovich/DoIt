package com.wokivovich.tm.repo;

import com.wokivovich.tm.entity.Task;
import com.wokivovich.tm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);


    @Query("select task from Task task inner join User on task.user.id = ?1 where task.isCompleted = false")
    List<Task> findUncompletedUserTasks(Long id);
}
