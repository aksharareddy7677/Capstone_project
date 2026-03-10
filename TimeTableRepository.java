package com.smartclassroom.Smart_Classroom.repository;

import com.smartclassroom.Smart_Classroom.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TimeTableRepository extends JpaRepository<Timetable,Long> {
    boolean existsByDayAndTimeSlotAndClassroom(Day day, TimeSlot timeSlot, Classroom classroom);

    Optional<Timetable> findByTeacherAndClassroomAndSubjectAndSectionAndDayAndTimeSlot(
            User teacher,
            Classroom classroom,
            Subject subject,
            Section section,
            Day day,
            TimeSlot timeSlot
    );


    List<Timetable> findBySectionAndClassroom(Section section, Classroom classroom);

}
