package com.example.demo.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class StudentModel {

    private Long id;
    @NotBlank(message = "name is required")
    private String name;
    @Email(message = "invalid email")
    @NotBlank(message = "email is required")
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public StudentModel(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public StudentModel() {
    }
}
