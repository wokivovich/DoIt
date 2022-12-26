package com.wokivovich.tm.service;


import com.wokivovich.tm.entity.Task;
import com.wokivovich.tm.exception.EntityNotFoundException;
import com.wokivovich.tm.repo.TaskRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepo taskRepo;

    @InjectMocks
    private final TaskService taskService = new TaskService(taskRepo);

    @Test
    void completeTask_uncompletedTask_taskStatusChangedToCompleted() {
        Task task = Task.builder()
                .id(1L)
                .description("Test")
                .isCompleted(false)
                .completionDate(LocalDate.now())
                .build();

        when(taskRepo.findById(1L)).thenReturn(Optional.ofNullable(task));
        when(taskRepo.save(task)).thenReturn(task);

        taskService.completeTask(1L);
        assertThat(task.isCompleted()).isTrue();

        verify(taskRepo).findById(1L);
        verify(taskRepo).save(task);
    }

    @Test
    void completeTask_incorrectTaskId_NotFoundException() {
        when(taskRepo.findById(1L)).thenThrow(new EntityNotFoundException("Can't find this task"));

        Assertions.assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> {
                taskService.completeTask(1L);
                }).withMessage("Can't find this task");

        verify(taskRepo, never()).save(any());

    }

    @Test
    void getTodayTasks_oneSimpleTask_returnsOneTask() {
        Task task = Task.builder()
                .id(1L)
                .description("today task")
                .isCompleted(false)
                .completionDate(LocalDate.now())
                .build();

        when(taskRepo.findByUserId(1L)).thenReturn(List.of(task));

        List<Task> actual = taskService.getTodayTasks(1L);

        assertThat(actual).isEqualTo(List.of(task));

        verify(taskRepo).findByUserId(1L);
    }

    @Test
    void getTomorrowTasks_oneSimpleTask_returnsOneTask() {
        Task task = Task.builder()
                .id(1L)
                .description("tomorrow task")
                .isCompleted(false)
                .completionDate(LocalDate.now().plusDays(1))
                .build();

        when(taskRepo.findByUserId(1L)).thenReturn(List.of(task));

        List<Task> actual = taskService.getTomorrowTasks(1L);

        assertThat(actual).isEqualTo(List.of(task));

        verify(taskRepo).findByUserId(1L);
    }

    @Test
    void getTomorrowTasks_twoTasks_returnsOneTask() {
        Task task1 = Task.builder()
                .id(1L)
                .description("tomorrow task")
                .isCompleted(false)
                .completionDate(LocalDate.now().plusDays(1))
                .build();
        Task task2 = Task.builder()
                .id(2L)
                .description("yesterday task")
                .isCompleted(false)
                .completionDate(LocalDate.now().minusDays(1))
                .build();

        when(taskRepo.findByUserId(1L)).thenReturn(List.of(task1, task2));

        List<Task> actual = taskService.getTomorrowTasks(1L);

        assertThat(actual.size()).isEqualTo(1);
        assertThat(actual).isEqualTo(List.of(task1));

        verify(taskRepo).findByUserId(1L);
    }
}
