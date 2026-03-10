package com.smartclassroom.Smart_Classroom.repository;

import com.smartclassroom.Smart_Classroom.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface MarksRepository extends JpaRepository<Marks,Long> {

    Optional<Marks> findByStudentAndSubjectAndExamType(
            User student,
            Subject subject,
            ExamType examType
    );
    List<Marks> findByStudentAndSubject(User student,Subject subject);

    List<Marks> findByStudent(User student);

    List<Marks> findByStudentAndExamType(User student, ExamType mid1);
}
