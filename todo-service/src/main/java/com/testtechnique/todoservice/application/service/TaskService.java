package com.testtechnique.todoservice.application.service;

import com.testtechnique.todoservice.application.port.in.TaskUseCases;
import com.testtechnique.todoservice.application.port.out.TaskRepository;
import com.testtechnique.todoservice.domain.exception.DomainException;
import com.testtechnique.todoservice.domain.exception.NotFoundException;
import com.testtechnique.todoservice.domain.model.Task;

import java.util.List;

import static com.testtechnique.todoservice.domain.policy.TaskPolicies.*;

public class TaskService implements TaskUseCases {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Task> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Task> getTodo() {
        return repository.findAll().stream().filter(t -> !t.completed()).toList();
    }

    @Override
    public Task getById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task not found: " + id));
    }

    @Override
    public Task add(String label, String description) {
        checkLabelMinLength(label);
        var all = repository.findAll();
        checkMaxActiveTasksNotExceeded(all);
        checkUniqueActiveLabel(all, label);
        return repository.save(Task.newTask(label.trim(), description));
    }

    @Override
    public Task changeStatus(String id, boolean completed) {
        var existing = getById(id);

        if (existing.completed() && !completed) {
            var all = repository.findAll();
            checkMaxActiveTasksNotExceeded(all);
            checkUniqueActiveLabel(all, existing.label());
        }

        return repository.save(existing.withCompleted(completed));
    }
    @Override
    public Task update(String id, String label, String description, boolean completed) {

        var task = repository.findById(id)
                .orElseThrow(() -> new DomainException("TASK_NOT_FOUND", "Task not found"));

        var updated = task.update(label, description, completed);

        return repository.save(updated);
    }

}
