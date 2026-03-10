package com.smartclassroom.Smart_Classroom.dto;

import com.smartclassroom.Smart_Classroom.model.User;
import lombok.Data;

@Data
public class AttendanceReportDTO {
    private User student;
    private double percentage;
    public AttendanceReportDTO(User student, double percentage) {
        this.student = student;
        this.percentage = percentage;
    }
}
