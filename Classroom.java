package com.smartclassroom.Smart_Classroom.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Classroom")
@Data
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)

    private String name;
    @Column(nullable = false,unique = true)

    private String section;
    @Column(nullable = false)

    private int year;
}
