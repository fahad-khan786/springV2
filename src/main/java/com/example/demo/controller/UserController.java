package com.example.demo.controller;

import com.example.demo.entity.Student;
import com.example.demo.model.StudentModel;
import com.example.demo.services.AemService;
import com.example.demo.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    StudentService studentService;

    @Autowired
    AemService aemService;

    @GetMapping("/getStudents")
    public List<Student> getStudentDetails(){
        return studentService.getAllStudents();
    }

    @PostMapping(value = "/addStudent",consumes = "application/json", produces = "application/json")
    public Student addStudent(@RequestBody StudentModel student){
        return studentService.createStudent(student);
    }

    @PostMapping(value = "/updateStudent",consumes = "application/json", produces = "application/json")
    public String updateStudent(@RequestBody StudentModel student){
        return studentService.updateStudent(student);
    }

    @PostMapping(value = "/deleteStudent/{id}")
    public String deleteStudent(@PathVariable("id") Long id){
        return studentService.deleteStudent(id);
    }

    @GetMapping(value = "/searchContent",produces = "application/json")
    public String getSearchContent(){
        return aemService.getAemSearchService();
    }

    @GetMapping(value = "/hello")
    public String getValue(){
        return "Hi Fahad";
    }

}
