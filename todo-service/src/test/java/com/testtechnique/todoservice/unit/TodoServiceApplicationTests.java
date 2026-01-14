package com.testtechnique.todoservice.unit;

import com.testtechnique.todoservice.application.port.out.TaskRepository;
import com.testtechnique.todoservice.application.service.TaskService;
import com.testtechnique.todoservice.domain.exception.DomainException;
import com.testtechnique.todoservice.domain.model.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TodoServiceApplicationTests {

    @Mock
    TaskRepository repo;

    @InjectMocks
    TaskService service;

    @Test
    void should_add_task() {
        when(repo.findAll()).thenReturn(List.of());
        when(repo.save(any())).thenAnswer(i -> i.getArgument(0));

        var task = service.add("Valid label", "desc");

        assertThat(task.label()).isEqualTo("Valid label");
    }

    @Test
    void should_reject_short_label() {

        assertThatThrownBy(() -> service.add("abc", "x"))
                .isInstanceOf(DomainException.class);
    }

    @Test
    void should_update_task() {

        var existing = Task.newTask("Old", "Old description");

        when(repo.findById(existing.id())).thenReturn(Optional.of(existing));
        when(repo.save(any())).thenAnswer(i -> i.getArgument(0));

        var updated = service.update(existing.id(), "New", "New description", true);

        assertThat(updated.label()).isEqualTo("New");
        assertThat(updated.description()).isEqualTo("New description");
        assertThat(updated.completed()).isTrue();
    }

    @Test
    void should_fail_when_task_not_found() {

        when(repo.findById("x")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update("x", "a", "b", false))
                .isInstanceOf(DomainException.class);
    }


}
