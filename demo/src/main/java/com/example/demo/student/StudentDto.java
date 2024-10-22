package com.example.demo.student;

import jakarta.validation.constraints.NotEmpty;

public record StudentDto(
        @NotEmpty ( message = "Firstname cannot be empty")
        String firstname,
        @NotEmpty
        String lastname,
        String email,
        int schoolId
) {

}
