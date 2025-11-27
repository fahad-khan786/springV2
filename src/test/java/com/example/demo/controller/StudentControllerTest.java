package com.example.demo.controller;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.StudentModel;
import com.example.demo.security.JwtService;
import com.example.demo.services.AemService;
import com.example.demo.services.CustomUserDetailsService;
import com.example.demo.services.ReviewService;
import com.example.demo.services.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class StudentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StudentService service;

    @MockBean
    ReviewService reviewService;

    @MockBean
    AemService aemService;

    @MockBean
    private CustomUserDetailsService userDetailsService;

    @MockBean
    private JwtService jwtService;


    private String asJson(String name, String email) {
        return "{ \"name\": \"" + name + "\", \"email\": \"" + email + "\" }";
    }

    // -------------------------------------------------------------------------
    // GET /st/getStudents/{id}
    // -------------------------------------------------------------------------
    @Test
    void testGetStudentSuccess() throws Exception {
        StudentModel model = new StudentModel(1L, "Rahul", "rahul@gmail.com");

        Mockito.when(service.getById(1L)).thenReturn(model);

        mvc.perform(get("/api/st/getStudents/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Rahul"));
    }

    @Test
    void testGetStudentNotFound() throws Exception {
        Mockito.when(service.getById(5L))
                .thenThrow(new ResourceNotFoundException("Student not found"));

        mvc.perform(get("/api/st/getStudents/5"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // -------------------------------------------------------------------------
    // POST /st/addStudent
    // -------------------------------------------------------------------------
    @Test
    void testAddStudentSuccess() throws Exception {
        StudentModel created = new StudentModel(10L, "Aman", "aman@gmail.com");

        Mockito.when(service.create(any(StudentModel.class)))
                .thenReturn(created);

        mvc.perform(post("/api/st/addStudent")
                        .content(asJson("Aman", "aman@gmail.com"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10L));
    }

    @Test
    void testAddStudentDuplicateEmail() throws Exception {
        Mockito.when(service.create(any(StudentModel.class)))
                .thenThrow(new BadRequestException("email already exist"));


        mvc.perform(post("/api/st/addStudent")
                        .content(asJson("Aman", "duplicate@gmail.com"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    void testUpdateStudentSuccess() throws Exception {
        StudentModel updated = new StudentModel(1L, "Updated", "updated@gmail.com");

        Mockito.when(service.update(eq(1L), any(StudentModel.class)))
                .thenReturn(updated);

        mvc.perform(post("/api/st/updateStudent/1")
                        .content(asJson("Updated", "updated@gmail.com"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("updated@gmail.com"));
    }

    @Test
    void testUpdateStudentDuplicateEmail() throws Exception {
        Mockito.when(service.update(eq(1L), any(StudentModel.class)))
                .thenThrow(new BadRequestException("email already exists"));

        mvc.perform(post("/api/st/updateStudent/1")
                        .content(asJson("Aman", "duplicate@gmail.com"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateStudentNotFound() throws Exception {
        Mockito.when(service.update(eq(5L), any(StudentModel.class)))
                .thenThrow(new ResourceNotFoundException("Student not found"));

        mvc.perform(post("/api/st/updateStudent/5")
                        .content(asJson("Test", "test@gmail.com"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // -------------------------------------------------------------------------
    // POST /st/deleteStudent/{id}
    // -------------------------------------------------------------------------
    @Test
    void testDeleteStudentSuccess() throws Exception {
        Mockito.when(service.delete(1L)).thenReturn("Student deleted");

        mvc.perform(post("/api/st/deleteStudent/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Student deleted"));
    }

    @Test
    void testDeleteStudentNotFound() throws Exception {
        Mockito.when(service.delete(9L))
                .thenThrow(new ResourceNotFoundException("Student not found"));

        mvc.perform(post("/api/st/deleteStudent/9"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // -------------------------------------------------------------------------
    // GET /st/search
    // -------------------------------------------------------------------------
    @Test
    void testSearchStudents() throws Exception {

        StudentModel s1 = new StudentModel(1L, "Rahul", "rahul@gmail.com");
        StudentModel s2 = new StudentModel(2L, "Aman", "aman@gmail.com");

        Page<StudentModel> page = new PageImpl<>(Arrays.asList(s1, s2));

        Mockito.when(service.search(eq("a"), any(Pageable.class)))
                .thenReturn(page);

        mvc.perform(get("/api/st/search?q=a&page=0&size=10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Rahul"))
                .andExpect(jsonPath("$.content[1].name").value("Aman"));
    }

}
