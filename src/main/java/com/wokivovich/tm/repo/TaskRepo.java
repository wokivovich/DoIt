package com.wokivovich.tm.repo;

import com.wokivovich.tm.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Long> {

    List<Task> findByUserId(Long id);
}
