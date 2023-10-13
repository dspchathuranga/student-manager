package com.cbrain.controller.dto;

import java.util.List;

public record StudentDto(Integer studentId, String studentFirstName,
                         String studentLastName, Integer studentAge,
                         String activeStatus, List<SubjectDto> studentSubjects) {
}
