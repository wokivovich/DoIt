package com.wokivovich.tm.repo;

import com.wokivovich.tm.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepo extends JpaRepository<Task, Long> {
}
