package com.wokivovich.tm.controller;

import com.wokivovich.tm.entity.Task;
import com.wokivovich.tm.entity.User;
import com.wokivovich.tm.repo.TaskRepo;
import com.wokivovich.tm.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    private UserRepo userRepo;
    private TaskRepo taskRepo;

    @Autowired
    public MainController(UserRepo userRepo, TaskRepo taskRepo) {
        this.userRepo = userRepo;
        this.taskRepo = taskRepo;
    }

    @GetMapping("/")
    public String main(Authentication authentication, Model model) {
        model.addAttribute("username", authentication.getName());
        User user = userRepo.findByUsername(authentication.getName()).orElseThrow();
        List<Task> tasks = userRepo.findById(user.getId()).get().getTasks();
        List<Task> completed = new ArrayList<>();
        tasks.forEach(task -> {
            if (!task.isCompleted()) {
                completed.add(task);
            }
        });
        model.addAttribute("tasks", completed);
        return "main.html";
    }

    @PostMapping("/delete")
    public String completeTask(@RequestParam String id) {
        System.out.println(id + "");
        Task task = taskRepo.findById(Long.parseLong(id)).orElseThrow(() -> new EntityNotFoundException("Cant find this task"));
        task.setCompleted(true);
        taskRepo.save(task);
        return "redirect:/";
    }

    @PostMapping("/new-task")
    public String newTask(@ModelAttribute Task task, Authentication authentication) {
        taskRepo.save(Task.builder()
                .description(task.getDescription())
                .isCompleted(false)
                .user(userRepo.findByUsername(authentication.getName()).get())
                .build());
        return "redirect:/";
    }

}
