package com.smartclassroom.Smart_Classroom.service;

import com.smartclassroom.Smart_Classroom.model.Classroom;
import com.smartclassroom.Smart_Classroom.repository.ClassroomRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassroomService {
    private final ClassroomRepository classroomRepository;
    public ClassroomService(ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }
    public List<Classroom> getAllClassrooms() {
        return classroomRepository.findAll();
    }


}
