package com.testtechnique.todoservice.commons.dto;

public record TaskUpdateRequest(
        String label,
        String description,
        boolean completed
) {}
