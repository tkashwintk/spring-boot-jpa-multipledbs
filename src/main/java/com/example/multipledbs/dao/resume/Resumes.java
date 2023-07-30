package com.example.multipledbs.dao.resume;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "resumes")
@Data
public class Resumes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
}
