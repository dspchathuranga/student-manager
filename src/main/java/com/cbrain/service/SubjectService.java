package com.cbrain.service;

import com.cbrain.controller.dto.SubjectRequestDto;
import com.cbrain.controller.dto.SubjectResponseDto;

import java.util.List;

public interface SubjectService {
    SubjectResponseDto createSubject(SubjectRequestDto subjectRequestDto);

    SubjectResponseDto getSubject(Integer subjectId);

    SubjectResponseDto updateSubject(Integer subjectId, SubjectRequestDto subjectRequestDto);

    void deleteSubject(Integer subjectId);

    List<SubjectResponseDto> getAllSubjectsByActiveStatus(String activeStatus);

    List<SubjectResponseDto> getAllSubjects();
}
