package com.smartclassroom.Smart_Classroom.model;

import jakarta.persistence.*;
import lombok.Data;

import static com.smartclassroom.Smart_Classroom.model.Role.TEACHER;

@Entity
@Table(name = "Subject")
@Data
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;
    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;


    @ManyToOne
    @JoinColumn(name = "teacher_id",nullable = false)
    private User teacher;


}
