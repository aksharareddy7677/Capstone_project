package com.smartclassroom.Smart_Classroom.controller;

import com.smartclassroom.Smart_Classroom.dto.AttendanceReportDTO;
import com.smartclassroom.Smart_Classroom.dto.StudentAttendanceDTO;
import com.smartclassroom.Smart_Classroom.model.*;
import com.smartclassroom.Smart_Classroom.repository.SubjectRepository;
import com.smartclassroom.Smart_Classroom.repository.UserRepository;
import com.smartclassroom.Smart_Classroom.service.AttendanceService;
import com.smartclassroom.Smart_Classroom.service.MarksService;
import com.smartclassroom.Smart_Classroom.service.TimetableService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/student")
public class StudentController {
private final UserRepository userRepository;
private final AttendanceService attendanceService;
private final SubjectRepository subjectRepository;
private final TimetableService timetableService;
private final MarksService marksService;
public StudentController(UserRepository userRepository,AttendanceService attendanceService,SubjectRepository subjectRepository
, TimetableService timetableService,MarksService marksService) {
    this.userRepository = userRepository;
    this.attendanceService = attendanceService;
    this.subjectRepository = subjectRepository;
    this.timetableService = timetableService;
    this.marksService = marksService;
}
    @GetMapping("/dashboard")
    public String dashboard(){
        return "studentDashboard";
    }

    //VIEW ATTENDANCE
    @GetMapping("/attendance")
    public String attendanceForm(Model model, Authentication authentication){
        String email=authentication.getName();

        User student=userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        Section section=student.getSection();
        Classroom classroom=student.getClassroom();

        List<StudentAttendanceDTO> attendanceSummary=attendanceService.getAttendanceSummary(student);
        model.addAttribute("attendanceSummary",attendanceSummary);
        double totalAttendance=0;
        double sum=0;
        for(StudentAttendanceDTO studentAttendanceDTO:attendanceSummary){
            sum+=studentAttendanceDTO.getPercentage();
        }
        totalAttendance=(sum/attendanceSummary.size());
        model.addAttribute("totalAttendance",totalAttendance);
        return "studentAttendanceForm";
    }

    //VIEW TIMETABLE
    @GetMapping("/timetable")
    public String timetableForm(Model model,Authentication authentication){
        String email=authentication.getName();
        User student=userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        Section section=student.getSection();
        Classroom classroom=student.getClassroom();

        Map<Day, Map<TimeSlot, String>> timetableGrid =
                timetableService.getFormattedTimetable(
                        student.getSection(),
                        student.getClassroom()
                );

        model.addAttribute("timetableGrid", timetableGrid);
        model.addAttribute("timeSlots", TimeSlot.values());

        return "timetableOfStudent";
    }

    //VIEW MARKS
    @GetMapping("/marks")
    public String marksForm(Model model,Authentication authentication){
    String email=authentication.getName();
        User student=userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        Section section=student.getSection();
        Classroom classroom=student.getClassroom();

        List<Marks> mid1Marks=marksService.getMarksByStudent(student,ExamType.MID1);
        model.addAttribute("mid1Marks", mid1Marks);

        List<Marks> mid2Marks=marksService.getMarksByStudent(student,ExamType.MID2);
        model.addAttribute("mid2Marks", mid2Marks);


        List<Marks> finalMarks=marksService.getMarksByStudent(student,ExamType.FINAL);
        model.addAttribute("finalMarks", finalMarks);

        double cgpa=0;

        int mid1=0;
        int mid2=0;
        int finalMar=0;
        for(int i=0;i<finalMarks.size();i++){
            mid1+=mid1Marks.get(i).getMarks();
            mid2+=mid2Marks.get(i).getMarks();
            finalMar+=finalMarks.get(i).getMarks();
        }
        double midAvg=(double) (mid1+mid2)/2;
        double total=midAvg + (double)  finalMar;

        cgpa=total/(finalMarks.size()*10);
        model.addAttribute("cgpa",cgpa);

        return "marksFormOfStudent";
    }

}
