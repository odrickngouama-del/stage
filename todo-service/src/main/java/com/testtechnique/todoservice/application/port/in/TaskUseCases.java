package com.testtechnique.todoservice.application.port.in;

import com.testtechnique.todoservice.domain.model.Task;

import java.util.List;

public interface TaskUseCases {
    List<Task> getAll();
    List<Task> getTodo();
    Task getById(String id);
    Task add(String label, String description);
    Task update(String id, String label, String description, boolean completed);
    Task changeStatus(String id, boolean completed);

}
