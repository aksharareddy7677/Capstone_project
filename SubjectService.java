package com.smartclassroom.Smart_Classroom.service;

import com.smartclassroom.Smart_Classroom.model.Subject;
import com.smartclassroom.Smart_Classroom.repository.SubjectRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public  SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public List<Subject>   getAllSubjects() {
        return subjectRepository.findAll();
    }
}
