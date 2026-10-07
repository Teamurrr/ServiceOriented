package com.example.demo.component;

import com.example.demo.entity.Task;
import com.example.demo.repository.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DatabaseTestRunner implements CommandLineRunner {

    private final TaskRepository taskRepository;

    public DatabaseTestRunner(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void run(String... args) {
        Task task = Task.builder()
                .name("Database Test")
                .description("Task check")
                .dueDate(LocalDate.now().plusDays(1))
                .completed(false)
                .build();

        Task savedTask = taskRepository.save(task);

        taskRepository.findById(savedTask.getId())
                .ifPresentOrElse(
                        foundTask -> System.out.println(
                                "Database test passed. Task found: " + foundTask.getName()
                        ),
                        () -> System.out.println("Database test failed. Task was not found.")
                );
    }
}
