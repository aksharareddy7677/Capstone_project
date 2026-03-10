package com.smartclassroom.Smart_Classroom.controller;

import com.smartclassroom.Smart_Classroom.dto.AttendanceReportDTO;
import com.smartclassroom.Smart_Classroom.model.*;
import com.smartclassroom.Smart_Classroom.repository.*;
import com.smartclassroom.Smart_Classroom.service.AdminService;
import com.smartclassroom.Smart_Classroom.service.AttendanceService;
import com.smartclassroom.Smart_Classroom.service.MarksService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/api/teacher")
public class TeacherController {

    private final AdminService adminService;
    private final ClassroomRepository classroomRepository;
    private final SubjectRepository subjectRepository;
  private final AttendanceRepository attendanceRepository;
  private final UserRepository userRepository;
private final TimeTableRepository timeTableRepository;
private final MarksRepository marksRepository;
private final MarksService marksService;
private final AttendanceService attendanceService;
    public TeacherController(AdminService adminService,ClassroomRepository classroomRepository,SubjectRepository subjectRepository
    ,AttendanceRepository attendanceRepository,
                             TimeTableRepository timeTableRepository,
                             UserRepository userRepository,MarksRepository marksRepository,MarksService marksService,
                             AttendanceService attendanceService) {
        this.adminService = adminService;
        this.classroomRepository=classroomRepository;
        this.subjectRepository=subjectRepository;
        this.attendanceRepository=attendanceRepository;
        this.timeTableRepository=timeTableRepository;
        this.userRepository=userRepository;
        this.marksRepository=marksRepository;
        this.marksService=marksService;
        this.attendanceService=attendanceService;
    }

    @GetMapping("/dashboard")
    public String dashboard(){
        return "teacherDashboard";
    }
    @GetMapping("/attendance")
    public String attendance(
            @RequestParam Long classroomId,
            @RequestParam Section section,
            @RequestParam Long subjectId,
            Model model) {

        Classroom classroom = classroomRepository.findById(classroomId).orElseThrow();

        List<User> students =
                userRepository.findByRoleAndClassroomAndSection(
                        Role.STUDENT,classroom, section);

        model.addAttribute("students", students);
        model.addAttribute("classroomId", classroomId);
        model.addAttribute("section", section);
        model.addAttribute("subjectId", subjectId);

        return "attendanceForm";
    }
@GetMapping("/attendance/select")
public String selectAttendance(Model model){
        model.addAttribute("classrooms",classroomRepository.findAll());
        model.addAttribute("sections",Section.values());
        model.addAttribute("subjects",subjectRepository.findAll());
return "attendanceSelect";
    }
    @PostMapping("/attendance")
    public String addAttendance(
            @RequestParam Long classroomId,
            @RequestParam Section section,
            @RequestParam Long subjectId,
            @RequestParam TimeSlot timeSlot,   // ✅ ADD THIS
            @RequestParam List<Long> studentIds,
            @RequestParam List<Status> statuses,
            Authentication authentication
    ) {
        Classroom classroom = classroomRepository.findById(classroomId).orElseThrow();
        Subject subject = subjectRepository.findById(subjectId).orElseThrow();

        String username = authentication.getName();

        User teacher = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        for (int i = 0; i < studentIds.size(); i++) {
            User student = adminService.findById(studentIds.get(i)).orElseThrow();

            Attendance attendance = new Attendance();
            attendance.setStudent(student);
            attendance.setSubject(subject);
            attendance.setClassroom(classroom);
            attendance.setSection(section);
            attendance.setTimeSlot(timeSlot);   // ✅ NOW SAFE
            attendance.setDate(LocalDate.now());
            attendance.setStatus(statuses.get(i));

            attendanceRepository.save(attendance);
        }

        return "redirect:/api/teacher/dashboard";
    }

    @GetMapping("/marks/select")
    public String selectMarks(Model model){
        model.addAttribute("classrooms",classroomRepository.findAll());
        model.addAttribute("sections",Section.values());
        model.addAttribute("subjects",subjectRepository.findAll());
        model.addAttribute("examTypes",ExamType.values());

        return "marksSelect";
    }

    @GetMapping("/marks")
    public String marksForm(
            @RequestParam Long classroomId,
            @RequestParam Section section,
            @RequestParam Long subjectId,
            @RequestParam ExamType examType,
            Model model
    ){
        Classroom classroom = classroomRepository.findById(classroomId).orElseThrow();
        Subject subject = subjectRepository.findById(subjectId).orElseThrow();

        List<User> students = userRepository
                .findByRoleAndClassroomAndSection(Role.STUDENT, classroom, section);

        model.addAttribute("students",students);
        model.addAttribute("classroom",classroom);
        model.addAttribute("section",section);
        model.addAttribute("subject",subject);
        model.addAttribute("examType",examType);

        return "marksForm";
    }

    @PostMapping("/marks")
    public String saveMarks(
        @RequestParam Long classroomId,
        @RequestParam Section section,
        @RequestParam Long subjectId,
        @RequestParam ExamType examType,
        @RequestParam List<Long> studentIds,
        @RequestParam List<Integer> marks
    ){
    Classroom classroom=classroomRepository.findById(classroomId).orElseThrow();
    Subject subject=subjectRepository.findById(subjectId).orElseThrow();

        List<User> students = studentIds.stream()
                .map(id -> userRepository.findById(id).orElseThrow())
                .toList();
        marksService.saveMarks(
                classroom,
                section,
                subject,
                examType,
                students,
                marks
        );
        return "redirect:/api/teacher/dashboard";
    }

    //CLASS DETAILS
   @GetMapping("/class/select")
    public String selectClass(Model model){
        model.addAttribute("classrooms",classroomRepository.findAll());
        model.addAttribute("sections",Section.values());
        return "selectClassroom";
   }

   //VIEW CLASS DETAILS
    @GetMapping("/class")
    public String viewClassDetails(
            @RequestParam Long classroomId,
            @RequestParam Section section,
            Model model,Authentication authentication
    ){
        Classroom classroom = classroomRepository.findById(classroomId).orElseThrow();
        List<User> students=userRepository.findByRoleAndClassroomAndSection(
                Role.STUDENT,
                classroom,
                section
        );
        String email = authentication.getName();
        User teacher = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        Subject subject = subjectRepository.findByTeacher(teacher)
                .orElseThrow(() -> new RuntimeException("Subject not assigned to teacher"));

        List<AttendanceReportDTO> attendances=attendanceService.findAttendanceBySubject(subject,students);

        model.addAttribute("subject", subject);
    model.addAttribute("attendances",attendances);
        model.addAttribute("subject",subject);
        model.addAttribute("classroom",classroom);
        model.addAttribute("section",section);
        model.addAttribute("students",students);
        return "classDetails";
    }
}
