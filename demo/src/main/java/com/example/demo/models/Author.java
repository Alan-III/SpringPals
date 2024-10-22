package com.example.demo.models;

import lombok.*;

@Data
@NoArgsConstructor
public class Author {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private int age;
    
}
