package com.smartclassroom.Smart_Classroom.service;

import com.smartclassroom.Smart_Classroom.dto.AttendanceReportDTO;
import com.smartclassroom.Smart_Classroom.dto.StudentAttendanceDTO;
import com.smartclassroom.Smart_Classroom.model.Attendance;
import com.smartclassroom.Smart_Classroom.model.Status;
import com.smartclassroom.Smart_Classroom.model.Subject;
import com.smartclassroom.Smart_Classroom.model.User;
import com.smartclassroom.Smart_Classroom.repository.AttendanceRepository;
import com.smartclassroom.Smart_Classroom.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final SubjectRepository subjectRepository;
    public AttendanceService(AttendanceRepository attendanceRepository,SubjectRepository subjectRepository) {
        this.attendanceRepository = attendanceRepository;
        this.subjectRepository = subjectRepository;
    }


    public List<AttendanceReportDTO> findAttendanceBySubject(
            Subject subject,
            List<User> students
    ){
        List<AttendanceReportDTO> report=new ArrayList<>();
        for(User student:students){
            List<Attendance> attendances=attendanceRepository.findByStudentIdAndSubjectId(student.getId(), subject.getId());
        int total= attendances.size();
        int present=0;
        for(Attendance attendance:attendances){
            if(attendance.getStatus()==Status.PRESENT){
                present++;
            }
        }
        double percentage=0;
        if(total>0){
            percentage = ((double) present / total) * 100;
        }
            report.add(new AttendanceReportDTO(student, percentage));
        }
        return report;
    }


    public List<StudentAttendanceDTO> getAttendanceSummary(User student) {
        List<Attendance> records=attendanceRepository.findByStudent(student);
        List<Subject> subjects=subjectRepository.findAll();
        List<StudentAttendanceDTO> attendanceSummary=new ArrayList<>();

        for(Subject subject:subjects){
            int present=0;
            for(int i=0;i<records.size();i++){
                if(records.get(i).getStatus() ==Status.PRESENT){
                    present++;
                }
            }
            double percentage=0;
            if(records.size()>0){
                percentage = ((double) present /records.size()) * 100;
            }

           StudentAttendanceDTO record=new StudentAttendanceDTO(subject.getName(),percentage);
            attendanceSummary.add(record);
        }
        return attendanceSummary;
    }
}
