package com.example.multipledbs.dao.student;

import com.example.multipledbs.dao.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}