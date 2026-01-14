package com.testtechnique.todoservice.exposition.rest;


import com.testtechnique.todoservice.application.port.in.TaskUseCases;
import com.testtechnique.todoservice.commons.dto.TaskRequest;
import com.testtechnique.todoservice.commons.dto.TaskResponse;
import com.testtechnique.todoservice.commons.dto.TaskUpdateRequest;
import com.testtechnique.todoservice.commons.dto.UpdateStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskUseCases useCases;

    public TaskController(TaskUseCases useCases) {
        this.useCases = useCases;
    }


    @GetMapping
    public List<TaskResponse> getAll(@RequestParam(defaultValue = "false") boolean todoOnly) {
        var tasks = todoOnly ? useCases.getTodo() : useCases.getAll();
        return tasks.stream().map(TaskResponse::from).toList();
    }

    @GetMapping("/{id}")
    public TaskResponse getById(@PathVariable String id) {
        return TaskResponse.from(useCases.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse create(@RequestBody TaskRequest req) {
        return TaskResponse.from(useCases.add(req.label(), req.description()));
    }

    @PutMapping("/{id}")
    public TaskResponse update(
            @PathVariable String id,
            @RequestBody TaskUpdateRequest req
    ) {
        return TaskResponse.from(
                useCases.update(id, req.label(), req.description(), req.completed())
        );
    }


    @PatchMapping("/{id}/status")
    public TaskResponse updateStatus(@PathVariable String id,
                                     @RequestBody UpdateStatus req) {
        return TaskResponse.from(useCases.changeStatus(id, req.completed()));
    }
}
