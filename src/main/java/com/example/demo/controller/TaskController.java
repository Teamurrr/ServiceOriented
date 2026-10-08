package com.example.demo.controller;

import com.example.demo.model.TaskModel;
import com.example.demo.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public String listTasks(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "tasks";
    }

    @GetMapping("/task/new")
    public String newTask(Model model) {
        model.addAttribute("taskModel", new TaskModel());
        return "task-form";
    }

    @GetMapping({"/task/edit/{id}", "/tasks/edit/{id}"})
    public String editTask(@PathVariable Long id, Model model) {
        TaskModel task = taskService.findById(id);

        if (task == null) {
            return "redirect:/tasks";
        }

        model.addAttribute("taskModel", task);
        return "task-form";
    }

    @PostMapping("/task/save")
    public String saveTask(
            @Valid @ModelAttribute("taskModel") TaskModel taskModel,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "task-form";
        }

        taskService.save(taskModel);
        return "redirect:/tasks";
    }

    @PostMapping({"/task/delete/{id}", "/tasks/delete/{id}"})
    public String deleteTask(@PathVariable Long id) {
        if (taskService.findById(id) != null) {
            taskService.deleteById(id);
        }

        return "redirect:/tasks";
    }

    @GetMapping("/tasks/filter")
    public String filterTasks(
            @RequestParam("completed") boolean completed,
            Model model
    ) {
        List<TaskModel> filteredTasks =
                taskService.findByCompleted(completed);

        model.addAttribute("tasks", filteredTasks);
        return "tasks";
    }
}
