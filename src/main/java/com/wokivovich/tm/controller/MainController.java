package com.wokivovich.tm.controller;

import com.wokivovich.tm.entity.Task;
import com.wokivovich.tm.entity.User;
import com.wokivovich.tm.service.TaskService;
import com.wokivovich.tm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    private UserService userService;
    private TaskService taskService;

    @Autowired
    public MainController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping("/today")
    public String main(Authentication authentication, Model model) {

        User user = userService.findByUsername(authentication.getName());

        model.addAttribute("username", authentication.getName());
        model.addAttribute("tasks", taskService.getTodayTasks(user.getId()));

        return "main.html";
    }

    @GetMapping("/tomorrow")
    public String tomorrow(Authentication authentication, Model model) {

        User user = userService.findByUsername(authentication.getName());

        model.addAttribute("username", authentication.getName());
        model.addAttribute("tasks", taskService.getTomorrowTasks(user.getId()));

        return "main.html";
    }

    @PostMapping("/delete")
    public String completeTask(@RequestParam Long id) {
        taskService.completeTask(id);

        return "redirect:/today";
    }

    @PostMapping("/new-task")
    public String newTask(@ModelAttribute Task task, Authentication authentication) {
        taskService.createNewTask(task, userService.findByUsername(authentication.getName()));

        return "redirect:/today";
    }

}
