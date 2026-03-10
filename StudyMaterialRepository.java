package com.smartclassroom.Smart_Classroom.repository;

import com.smartclassroom.Smart_Classroom.model.StudyMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyMaterialRepository extends JpaRepository<StudyMaterial,Long> {
}
