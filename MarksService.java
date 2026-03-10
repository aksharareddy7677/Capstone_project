package com.smartclassroom.Smart_Classroom.service;

import com.smartclassroom.Smart_Classroom.model.*;
import com.smartclassroom.Smart_Classroom.repository.MarksRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarksService {
    private final  MarksRepository marksRepository;
    public MarksService(MarksRepository marksRepository) {
        this.marksRepository = marksRepository;
    }

    public void saveMarks(Classroom classroom,
                          Section section,
                          Subject subject,
                          ExamType examType,
                          List<User> students,
                          List<Integer> marksList){
        for (int i=0;i< students.size();i++){
            User student=students.get(i);
            Integer marksValue= marksList.get(i);
            if(marksValue==null){
                continue;
            }
            Marks marks=marksRepository
                    .findByStudentAndSubjectAndExamType(student,subject,examType)
                    .orElse(new Marks());
            marks.setStudent(student);
            marks.setSubject(subject);
            marks.setClassroom(classroom);
            marks.setSection(section);
            marks.setExamType(examType);
            marks.setMarks(marksValue);

            marksRepository.save(marks);
        }
    }

    public List<Marks> getMarksByStudent(User student, ExamType mid1) {
        return marksRepository.findByStudentAndExamType(student,mid1);
    }
}
