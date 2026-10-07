package com.example.demo.controller;

import com.example.demo.model.TaskModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TaskController {

    private final List<TaskModel> tasks = new ArrayList<>();
    private long nextId = 3L;

    public TaskController() {
        tasks.add(new TaskModel(
                1L,
                "Test Task 1",
                "Description for Task 1",
                LocalDate.now(),
                false
        ));

        tasks.add(new TaskModel(
                2L,
                "Test Task 2",
                "Description for Task 2",
                LocalDate.now().plusDays(1),
                true
        ));
    }

    @GetMapping("/tasks")
    public String listTasks(Model model) {
        model.addAttribute("tasks", tasks);
        return "tasks";
    }

    @GetMapping("/task/new")
    public String newTask(Model model) {
        model.addAttribute("taskModel", new TaskModel());
        return "task-form";
    }

    @GetMapping("/task/edit/{id}")
    public String editTask(@PathVariable Long id, Model model) {
        TaskModel task = tasks.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (task == null) {
            return "redirect:/tasks";
        }

        model.addAttribute("taskModel", task);
        return "task-form";
    }

    @PostMapping("/task/save")
    public String saveTask(@ModelAttribute TaskModel taskModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "task-form";
        }

        if (taskModel.getId() == null) {
            taskModel.setId(nextId++);
            tasks.add(taskModel);
        } else {
            int existingIndex = -1;
            for (int i = 0; i < tasks.size(); i++) {
                if (tasks.get(i).getId().equals(taskModel.getId())) {
                    existingIndex = i;
                    break;
                }
            }

            if (existingIndex >= 0) {
                tasks.set(existingIndex, taskModel);
            } else {
                tasks.add(taskModel);
                nextId = Math.max(nextId, taskModel.getId() + 1);
            }
        }

        return "redirect:/tasks";
    }
}
