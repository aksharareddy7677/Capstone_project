package com.smartclassroom.Smart_Classroom.service;

import com.smartclassroom.Smart_Classroom.model.*;
import com.smartclassroom.Smart_Classroom.repository.ClassroomRepository;
import com.smartclassroom.Smart_Classroom.repository.SubjectRepository;
import com.smartclassroom.Smart_Classroom.repository.TimeTableRepository;
import com.smartclassroom.Smart_Classroom.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final ClassroomRepository classroomRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final TimeTableRepository timeTableRepository;

    public AdminService(TimeTableRepository timeTableRepository,ClassroomRepository classroomRepository,UserRepository userRepository,BCryptPasswordEncoder bCryptPasswordEncoder, SubjectRepository subjectRepository) {
        this.timeTableRepository = timeTableRepository;
        this.classroomRepository = classroomRepository;
        this.userRepository=userRepository;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
        this.subjectRepository=subjectRepository;
    }

    public void createClassroom(Classroom classroom) {
        classroomRepository.save(classroom);
    }

    public void addTeacher(User teacher) {

        teacher.setPassword(bCryptPasswordEncoder.encode(teacher.getPassword()));
        teacher.setRole(Role.TEACHER);
        userRepository.save(teacher);
    }

    public void addStudent(User student) {
        student.setPassword(bCryptPasswordEncoder.encode(student.getPassword()));
        student.setRole(Role.STUDENT);
        userRepository.save(student);
    }



    public void addSubject(String name, Long teacherId, Long classroomId) {
   User teacher=userRepository.findById(teacherId) .orElseThrow(() -> new RuntimeException("Teacher not found"));
   Classroom classroom=classroomRepository.findById(classroomId).orElseThrow(() -> new RuntimeException("Classroom not found"));

   Subject subject=new Subject();
   subject.setName(name);
   subject.setTeacher(teacher);
   subject.setClassroom(classroom);

   subjectRepository.save(subject);
    }

    public void addTimetable(
            Day day,
            Section section,
            TimeSlot timeSlot,
            Long classroomId,
            Long subjectId,
            Long teacherId
    ) {

        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("Classroom not found"));

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        User teacher = userRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        Timetable timetable = new Timetable();
        timetable.setDay(day);
        timetable.setSection(section);
        timetable.setTimeSlot(timeSlot);
        timetable.setClassroom(classroom);
        timetable.setSubject(subject);
        timetable.setTeacher(teacher);

        timeTableRepository.save(timetable);
    }


    public User getLoggedInUser(Authentication auth) {
        return (User) auth.getPrincipal();
    }

    public Optional<User> findById(Long studentId) {
        return  userRepository.findById(studentId);
    }

    public List<User> findAllStudents() {
        List<User> users = new ArrayList<>();
        for(User user : userRepository.findAll()) {
            if(user.getRole().equals(Role.STUDENT)) {
                users.add(user);
            }
        }
        return users;
    }


}
