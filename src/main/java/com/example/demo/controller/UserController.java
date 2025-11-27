package com.example.demo.controller;

import com.example.demo.entity.Student;
import com.example.demo.exception.BadRequestException;
import com.example.demo.model.ReviewData;
import com.example.demo.model.StudentModel;
import com.example.demo.services.AemService;
import com.example.demo.services.ReviewService;
import com.example.demo.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    ReviewService reviewService;

    @Autowired
    StudentService service;

    @Autowired
    AemService aemService;

    @GetMapping("/st/getStudents/{id}")
    public ResponseEntity<StudentModel> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping(value = "/st/addStudent",consumes = "application/json", produces = "application/json")
    public ResponseEntity<StudentModel> addStudent(@RequestBody @Valid StudentModel student){
        StudentModel created = service.create(student);
        return new ResponseEntity<>(created, HttpStatus.CREATED);    }

    @PostMapping(value = "/st/updateStudent/{id}",consumes = "application/json", produces = "application/json")
    public ResponseEntity<StudentModel> update(@PathVariable Long id, @RequestBody @Valid StudentModel dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PostMapping(value = "/st/deleteStudent/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> delete(@PathVariable Long id) {
        String message = service.delete(id);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/st/search")
    public Page<StudentModel> search(@RequestParam(defaultValue = "") String q,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        return service.search(q, PageRequest.of(page, size));
    }

    @GetMapping(value = "/searchContent",produces = "application/json")
    public String getSearchContent(){
        return aemService.getAemSearchService();
    }

    @GetMapping(value = "/fetchRating",produces = "application/json")
    public String fetchRatingContent(){
        return aemService.getAemFetchRatingService();
    }

    @GetMapping(value = "/user/hello")
    public String getValue(){
        return "Hi, i am fahad from userV2";
    }

    @GetMapping(value = "/admin/hello")
    public String getValueAdmin(){
        return "Hi, i am fahad from adminV2";
    }

    @PostMapping(value = "/review",consumes = "application/json", produces = "application/json")
    public String getReviewData(@RequestBody ReviewData reviewData){
        return reviewService.getReviewData(reviewData);
    }


    @PostMapping("/st/insert-default-100")
    public String insertDefaultStudents() {
        List<StudentModel> createdList = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            StudentModel dto = new StudentModel();
            dto.setName("Student " + i);
            dto.setEmail("student" + i + "@example.com");

            try {
                StudentModel created = service.create(dto);
                createdList.add(created);
            } catch (BadRequestException ex) {
                // email already exists → skip record
            }
        }

        return "Data created successfully";
    }
}
