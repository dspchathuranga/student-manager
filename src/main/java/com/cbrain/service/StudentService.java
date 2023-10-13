package com.cbrain.service;

import com.cbrain.controller.dto.StudentDto;

import java.util.List;

public interface StudentService {
    StudentDto createStudent(StudentDto studentDto);

    StudentDto getStudent(Integer studentId);

    StudentDto updateStudent(Integer studentId, StudentDto studentDto);

    void deleteStudent(Integer studentId);

    List<StudentDto> getAllStudents();

    List<StudentDto> getAllStudentsByActiveStatus(String activeStatus);
}
