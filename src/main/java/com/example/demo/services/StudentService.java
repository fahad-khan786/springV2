package com.example.demo.services;

import com.example.demo.entity.Student;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.StudentModel;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {


    @Autowired
    private UserRepository repo;

    private StudentModel toDto(Student s) {
        StudentModel d = new StudentModel();
        d.setId(s.getId());
        d.setName(s.getName());
        d.setEmail(s.getEmail());
        return d;
    }


    private Student toEntity(StudentModel d) {
        Student s = new Student();
        s.setName(d.getName());
        s.setEmail(d.getEmail());
        return s;
    }


    public StudentModel create(StudentModel dto) {
        if (repo.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("email already exists");
        }
        Student s = repo.save(toEntity(dto));
        return toDto(s);
    }


    public StudentModel getById(Long id) {
        Student s = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        return toDto(s);
    }



    public StudentModel update(Long id, StudentModel dto) {
        Student s = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        if (!s.getEmail().equals(dto.getEmail()) && repo.existsByEmail(dto.getEmail())) {
            throw new BadRequestException("email already exists");
        }
        s.setName(dto.getName()); s.setEmail(dto.getEmail());
        return toDto(repo.save(s));
    }


    public String delete(Long id) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Student not found with id: " + id);
        repo.deleteById(id);
        return "Student deleted successfully with id: " + id;
    }

    public Page<StudentModel> search(String query, Pageable pageable) {
        return repo.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query, pageable).map(this::toDto);
    }
}
