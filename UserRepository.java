package com.smartclassroom.Smart_Classroom.repository;

import com.smartclassroom.Smart_Classroom.model.*;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

    List<User> findByRole(Role role);

    List<User> findByRoleAndClassroomAndSection(Role role,
                                                Classroom classroom,
                                                Section section);

   }
