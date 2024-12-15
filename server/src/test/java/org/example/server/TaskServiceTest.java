package org.example.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        taskService = new TaskService(taskRepository);
    }

    @Test
    void createTask() {
        taskService.createTask("Test");

        ArgumentCaptor<Task> argumentCaptor = ArgumentCaptor.forClass(Task.class);

        verify(taskRepository).save(argumentCaptor.capture());
        assertEquals("Test", argumentCaptor.getValue().getDescription());
    }

    @Test
    void getAllTasks() {
        var tasks = List.of(new Task(1L, "", null, false));
        when(taskRepository.findAll()).thenReturn(tasks);
        var result = taskService.getAllTasks();
        assertEquals(tasks, result);
    }

    @Test
    void getTaskById() {
        var task = new Task(1L, "", null, false);
        when(taskRepository.findById(1L)).thenReturn(task);
        var result = taskService.getTaskById(1L);
        assertEquals(task, result);
    }
}