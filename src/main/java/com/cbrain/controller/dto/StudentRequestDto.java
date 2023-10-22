package com.cbrain.controller.dto;

import java.util.List;

public record StudentRequestDto(String studentFirstName,
                                String studentLastName, Integer studentAge,
                                String activeStatus, List<Integer> studentSubjectIds) {
}
