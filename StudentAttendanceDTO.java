package com.smartclassroom.Smart_Classroom.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentAttendanceDTO {
    private String subjectName;
    private Double percentage;

    public StudentAttendanceDTO(String subjectName, double percentage) {
        this.subjectName = subjectName;
        this.percentage = percentage;
    }




}
