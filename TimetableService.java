package com.smartclassroom.Smart_Classroom.service;

import com.smartclassroom.Smart_Classroom.model.*;
import com.smartclassroom.Smart_Classroom.repository.TimeTableRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class TimetableService {
    private final TimeTableRepository  timeTableRepository;
    public TimetableService(TimeTableRepository timeTableRepository) {
        this.timeTableRepository = timeTableRepository;
    }
    public List<Timetable> getTimetableBySectionAndClassroom(Section section, Classroom classroom) {
        return timeTableRepository.findBySectionAndClassroom(section,classroom);
    }
    public Map<Day, Map<TimeSlot, String>> getFormattedTimetable(
            Section section, Classroom classroom) {

        List<Timetable> entries =
                timeTableRepository.findBySectionAndClassroom(section, classroom);

        Map<Day, Map<TimeSlot, String>> timetableGrid = new LinkedHashMap<>();

        // Step 1: Create full empty grid
        for (Day day : Day.values()) {
            Map<TimeSlot, String> daySchedule = new LinkedHashMap<>();

            for (TimeSlot slot : TimeSlot.values()) {
                daySchedule.put(slot, ""); // default empty
            }

            timetableGrid.put(day, daySchedule);
        }

        // Step 2: Fill actual data from DB
        for (Timetable entry : entries) {
            timetableGrid
                    .get(entry.getDay())
                    .put(entry.getTimeSlot(),
                            entry.getSubject().getName());
        }

        return timetableGrid;
    }

}
