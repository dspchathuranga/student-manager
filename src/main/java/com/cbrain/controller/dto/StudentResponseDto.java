package com.cbrain.controller.dto;

import java.util.List;

public record StudentResponseDto(Integer studentId, String studentFirstName,
                                 String studentLastName, Integer studentAge,
                                 String activeStatus, List<SubjectResponseDto> studentSubjects) {
}
