package com.smartclassroom.Smart_Classroom.repository;

import com.smartclassroom.Smart_Classroom.model.Subject;
import com.smartclassroom.Smart_Classroom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject,Long> {
    Optional<Subject> findByTeacher(User teacher);

}
