package com.testtechnique.todoservice.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class TaskControllerIT {

    @Autowired
    private TestRestTemplate http;

    @Test
    void should_create_task() {
        var req = Map.of("label", "Integration Task", "description", "desc");

        var res = http.postForEntity("/api/tasks", req, Map.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(res.getBody().get("id")).isNotNull();
        assertThat(res.getBody().get("label")).isEqualTo("Integration Task");
        assertThat(res.getBody().get("completed")).isEqualTo(false);
    }

    @Test
    void should_get_all_tasks() {
        var res = http.getForEntity("/api/tasks", Map[].class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(res.getBody()).isNotNull();
    }

    @Test
    void should_reject_task_with_short_label() {
        var req = Map.of("label", "Task", "description", "desc");

        var res = http.postForEntity("/api/tasks", req, Map.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void should_get_incomplete_tasks() {
        var res = http.getForEntity("/api/tasks?todoOnly=true", Object[].class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(res.getBody()).isNotNull();
    }
}