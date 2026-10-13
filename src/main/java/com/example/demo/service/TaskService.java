package com.example.demo.service;

import com.example.demo.entity.Task;
import com.example.demo.model.TaskModel;
import com.example.demo.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;
@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<TaskModel> findAll() {
        return taskRepository.findAll()
                .stream()
                .map(this::convertToModel)
                .collect(Collectors.toList());
    }

    public TaskModel findById(Long id) {
        return taskRepository.findById(id)
                .map(this::convertToModel)
                .orElse(null);
    }

    public List<TaskModel> findByCompleted(boolean completed) {
        return taskRepository.findByCompleted(completed)
                .stream()
                .map(this::convertToModel)
                .collect(Collectors.toList());
    }


    public List<TaskModel> findByDueDateBetween(
            LocalDate startDate,
            LocalDate endDate
    ) {
        return taskRepository
                .findByDueDateBetween(startDate, endDate)
                .stream()
                .map(this::convertToModel)
                .collect(Collectors.toList());
    }

    public Task save(TaskModel taskModel) {
        Task entity = convertToEntity(taskModel);
        return taskRepository.save(entity);
    }

    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

    private TaskModel convertToModel(Task task) {
        if (task == null) {
            return null;
        }

        return TaskModel.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .dueDate(task.getDueDate())
                .completed(task.isCompleted())
                .build();
    }

    private Task convertToEntity(TaskModel taskModel) {
        if (taskModel == null) {
            return null;
        }

        return Task.builder()
                .id(taskModel.getId())
                .name(taskModel.getName())
                .description(taskModel.getDescription())
                .dueDate(taskModel.getDueDate())
                .completed(taskModel.isCompleted())
                .build();
    }
}