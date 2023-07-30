package com.example.multipledbs;

//import com.example.multipledbs.dao.resume.ResumeRepository;
//import com.example.multipledbs.dao.resume.Resumes;
import com.example.multipledbs.dao.resume.ResumeRepository;
import com.example.multipledbs.dao.resume.Resumes;
import com.example.multipledbs.dao.student.Student;
import com.example.multipledbs.dao.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AppController {
    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private StudentRepository studentRepository;
       
    @GetMapping("/students")
    public List<Student> fetchStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/resumes")
    public List<Resumes> fetchResumes() {
        return resumeRepository.findAll();
    }
       
}