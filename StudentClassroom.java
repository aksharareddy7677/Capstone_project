package com.smartclassroom.Smart_Classroom.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "student_classroom")
@Data
public class StudentClassroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id",nullable = false)
    private User student;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;
}
