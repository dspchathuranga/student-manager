package com.cbrain.service.impl;

import com.cbrain.controller.dto.StudentDto;
import com.cbrain.repository.StudentRepository;
import com.cbrain.repository.entity.StudentEntity;
import com.cbrain.service.StudentService;
import com.cbrain.utils.ObjectMapperUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public StudentDto createStudent(StudentDto studentDto) {
        return ObjectMapperUtility.mapToStudentDto(
                studentRepository.save(
                        ObjectMapperUtility.mapToStudentEntity(studentDto)
                )
        );
    }

    @Override
    public StudentDto getStudent(Integer studentId) {
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
    public StudentDto updateStudent(Integer studentId, StudentDto studentDto) {
        return ObjectMapperUtility.mapToStudentDto(
                studentRepository.save(
                        ObjectMapperUtility.mapToStudentEntity(studentId, studentDto)
                )
        );
    }

    @Override
    public void deleteStudent(Integer studentId) {
        studentRepository.deleteById(studentId);
    }

    @Override
    public List<StudentDto> getAllStudents() {
        return studentRepository.findAll().stream().map(
                studentEntity -> ObjectMapperUtility.mapToStudentDto(studentEntity)
        ).toList();
    }

    @Override
    public List<StudentDto> getAllStudentsByActiveStatus(String activeStatus) {
        return studentRepository.findAllByActiveStatus(activeStatus).stream().map(
                studentEntity -> ObjectMapperUtility.mapToStudentDto(studentEntity)
        ).toList();
    }
}
