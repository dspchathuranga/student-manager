package com.cbrain.service;

import com.cbrain.controller.dto.SubjectDto;

import java.util.List;

public interface SubjectService {
    SubjectDto createSubject(SubjectDto subjectDto);

    SubjectDto getSubject(Integer subjectId);

    SubjectDto updateSubject(Integer subjectId, SubjectDto subjectDto);

    void deleteSubject(Integer subjectId);

    List<SubjectDto> getAllSubjectsByActiveStatus(String activeStatus);

    List<SubjectDto> getAllSubjects();
}
