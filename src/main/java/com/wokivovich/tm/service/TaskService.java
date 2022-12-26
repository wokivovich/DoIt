package com.wokivovich.tm.service;

import com.wokivovich.tm.entity.Task;
import com.wokivovich.tm.entity.User;
import com.wokivovich.tm.exception.EntityNotFoundException;
import com.wokivovich.tm.repo.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private TaskRepo taskRepo;

    @Autowired
    public TaskService(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    @Transactional
    public void completeTask(Long id) {
        Task task = taskRepo.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find this task"));

        task.setCompleted(true);
        taskRepo.save(task);
    }

    public void createNewTask(Task task, User user) {
        taskRepo.save(Task.builder()
                .description(task.getDescription())
                .isCompleted(false)
                .completionDate(LocalDate.now())
                .user(user)
                .build());
    }

    public List<Task> getTodayTasks(Long id) {
        List<Task> todayTasks = taskRepo.findByUserId(id);

        return todayTasks.stream().filter(task ->
                task.getCompletionDate().equals(LocalDate.now()))
                .collect(Collectors.toList());
    }

    public List<Task> getTomorrowTasks(Long id) {
        List<Task> todayTasks = taskRepo.findByUserId(id);

        return todayTasks.stream().filter(task ->
                        task.getCompletionDate().equals(LocalDate.now().plusDays(1)))
                .collect(Collectors.toList());
    }
}
