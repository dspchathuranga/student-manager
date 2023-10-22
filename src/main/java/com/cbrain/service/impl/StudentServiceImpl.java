package com.cbrain.service.impl;

import com.cbrain.controller.dto.StudentRequestDto;
import com.cbrain.controller.dto.StudentResponseDto;
import com.cbrain.repository.StudentRepository;
import com.cbrain.repository.entity.StudentEntity;
import com.cbrain.service.StudentService;
import com.cbrain.utils.ObjectMapperUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public StudentResponseDto createStudent(StudentRequestDto studentRequestDto) {
        return ObjectMapperUtility.mapToStudentDto(
                studentRepository.save(
                        ObjectMapperUtility.mapToStudentEntity(studentRequestDto)
                )
        );
    }

    @Override
    public StudentResponseDto getStudent(Integer studentId) {
        StudentEntity studentEntity = studentRepository.findById(studentId).orElse(null);
        if (studentEntity != null) {
            return ObjectMapperUtility.mapToStudentDto(
                    studentEntity
            );
        } else {
            // Set the HTTP 404 Not Found status
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }

    }

    @Override
    public StudentResponseDto updateStudent(Integer studentId, StudentRequestDto studentRequestDto) {
        return ObjectMapperUtility.mapToStudentDto(
                studentRepository.save(
                        ObjectMapperUtility.mapToStudentEntity(studentId, studentRequestDto)
                )
        );
    }

    @Override
    public void deleteStudent(Integer studentId) {
        studentRepository.deleteById(studentId);
    }

    @Override
    public List<StudentResponseDto> getAllStudents() {
        return studentRepository.findAll().stream().map(
                studentEntity -> ObjectMapperUtility.mapToStudentDto(studentEntity)
        ).toList();
    }

    @Override
    public List<StudentResponseDto> getAllStudentsByActiveStatus(String activeStatus) {
        return studentRepository.findAllByActiveStatus(activeStatus).stream().map(
                studentEntity -> ObjectMapperUtility.mapToStudentDto(studentEntity)
        ).toList();
    }
}
