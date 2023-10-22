package com.cbrain.service;

import com.cbrain.controller.dto.StudentRequestDto;
import com.cbrain.controller.dto.StudentResponseDto;

import java.util.List;

public interface StudentService {
    StudentResponseDto createStudent(StudentRequestDto studentRequestDto);

    StudentResponseDto getStudent(Integer studentId);

    StudentResponseDto updateStudent(Integer studentId, StudentRequestDto studentRequestDto);

    void deleteStudent(Integer studentId);

    List<StudentResponseDto> getAllStudents();

    List<StudentResponseDto> getAllStudentsByActiveStatus(String activeStatus);
}
