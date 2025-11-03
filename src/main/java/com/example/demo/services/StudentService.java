package com.example.demo.services;

import com.example.demo.entity.Student;
import com.example.demo.model.StudentModel;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {


    @Autowired
    private UserRepository userRepository;

    public List<Student> getAllStudents() {
        return userRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return userRepository.findById(id);
    }

    public Student createStudent(StudentModel student) {
        Student st = new Student();
        st.setName(student.getName());
        st.setEmail(student.getEmail());
        return userRepository.save(st);
    }

    public String updateStudent(StudentModel studentDetails) {

        Optional<Student>  dataById =  userRepository.findById(studentDetails.getId());

        if (!(dataById.isPresent())){
            return "Data not found";
        }else{
            Student st =  dataById.get();
            st.setEmail(studentDetails.getEmail());
            st.setName(studentDetails.getName());
             userRepository.save(st);
             return "Data Updated successfully";
        }
    }

    public String deleteStudent(Long id) {
        Optional<Student> st =  userRepository.findById(id);
        if(st.isPresent()){
            userRepository.delete(st.get());
            return "deleted successfully";
        }else{
            return "data not found";
        }
    }
}
