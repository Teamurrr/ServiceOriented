package com.example.demo.model;

import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


public class TaskModel {

    private Long id;

    @NotBlank(message = "Name is required")
    @Size(
            min = 3,
            max = 100,
            message = "Name must be between min and 100 charactersd"
    )

    private String name;
    private String description;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dueDate;

    private boolean completed;

    public TaskModel() {
    }

    @Builder
    public TaskModel(
            Long id,
            String name,
            String description,
            LocalDate dueDate,
            boolean completed
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}