package com.cbrain.utils;

import com.cbrain.controller.dto.StudentRequestDto;
import com.cbrain.controller.dto.StudentResponseDto;
import com.cbrain.controller.dto.SubjectRequestDto;
import com.cbrain.controller.dto.SubjectResponseDto;
import com.cbrain.repository.entity.StudentEntity;
import com.cbrain.repository.entity.SubjectEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ObjectMapperUtility {
    public static StudentResponseDto mapToStudentDto(StudentEntity studentEntity, List<SubjectEntity> subjectEntities) {
        StudentResponseDto studentResponseDto = new StudentResponseDto(
                studentEntity.getStudentId(),
                studentEntity.getStudentFirstName(),
                studentEntity.getStudentLastName(),
                studentEntity.getStudentAge(),
                studentEntity.getActiveStatus(),
                subjectEntities.stream().map(subject -> mapToSubjectDto(subject)).toList()
        );
        return studentResponseDto;
    }

    public static StudentResponseDto mapToStudentDto(StudentEntity studentEntity) {
        StudentResponseDto studentResponseDto = new StudentResponseDto(
                studentEntity.getStudentId(),
                studentEntity.getStudentFirstName(),
                studentEntity.getStudentLastName(),
                studentEntity.getStudentAge(),
                studentEntity.getActiveStatus(),
                studentEntity.getSubjects().stream().map(subjectEntity -> mapToSubjectDto(subjectEntity)).toList()
        );
        return studentResponseDto;
    }

    public static SubjectResponseDto mapToSubjectDto(SubjectEntity subjectEntities) {
        return new SubjectResponseDto(
                subjectEntities.getSubjectId(),
                subjectEntities.getSubjectName(),
                subjectEntities.getActiveStatus()
        );
    }

    public static SubjectEntity mapToSubjectEntity(SubjectRequestDto subjectRequestDto) {
        return SubjectEntity.builder()
                .subjectId(0)
                .subjectName(subjectRequestDto.subjectName())
                .activeStatus(subjectRequestDto.activeStatus())
                .createDate(LocalDateTime.now())
                .build();
    }

    public static SubjectEntity mapToSubjectEntity(Integer subjectId) {
        return SubjectEntity.builder()
                .subjectId(subjectId)
                .build();
    }

    public static SubjectEntity mapToSubjectEntity(Integer subjectId, SubjectRequestDto subjectRequestDto) {
        return SubjectEntity.builder()
                .subjectId(subjectId)
                .subjectName(subjectRequestDto.subjectName())
                .activeStatus(subjectRequestDto.activeStatus())
                .createDate(LocalDateTime.now())
                .build();
    }

    public static StudentEntity mapToStudentEntity(StudentRequestDto studentRequestDto) {
        return StudentEntity.builder()
                .studentId(0)
                .studentFirstName(studentRequestDto.studentFirstName())
                .studentLastName(studentRequestDto.studentLastName())
                .studentAge(studentRequestDto.studentAge())
                .activeStatus(studentRequestDto.activeStatus())
                .createDate(LocalDateTime.now())
                .subjects(studentRequestDto.studentSubjectIds().stream().map(subjectId -> mapToSubjectEntity(subjectId)).toList())
                .build();
    }

    public static StudentEntity mapToStudentEntity(Integer studentId, StudentRequestDto studentRequestDto) {
        return StudentEntity.builder()
                .studentId(studentId)
                .studentFirstName(studentRequestDto.studentFirstName())
                .studentLastName(studentRequestDto.studentLastName())
                .studentAge(studentRequestDto.studentAge())
                .activeStatus(studentRequestDto.activeStatus())
                .createDate(LocalDateTime.now())
                .subjects(studentRequestDto.studentSubjectIds().stream().map(subjectId -> mapToSubjectEntity(subjectId)).toList())
                .build();
    }
}
