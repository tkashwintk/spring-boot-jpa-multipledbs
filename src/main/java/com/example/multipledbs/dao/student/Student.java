package com.example.multipledbs.dao.student;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "students")
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
}
