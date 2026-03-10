package com.smartclassroom.Smart_Classroom.repository;

import com.smartclassroom.Smart_Classroom.model.Attendance;
import com.smartclassroom.Smart_Classroom.model.Classroom;
import com.smartclassroom.Smart_Classroom.model.Section;
import com.smartclassroom.Smart_Classroom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByStudentIdAndSubjectId(Long studentId, Long subjectId);

    List<Attendance> findByStudent(User student);
}
