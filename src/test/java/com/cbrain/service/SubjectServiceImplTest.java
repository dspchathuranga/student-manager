package com.cbrain.service;

import com.cbrain.controller.dto.StudentDto;
import com.cbrain.controller.dto.SubjectDto;
import com.cbrain.repository.StudentRepository;
import com.cbrain.repository.SubjectRepository;
import com.cbrain.repository.entity.StudentEntity;
import com.cbrain.repository.entity.SubjectEntity;
import com.cbrain.service.impl.SubjectServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubjectServiceImplTest {

    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private SubjectServiceImpl subjectService;
    private SubjectEntity subject;

    private SubjectDto subjectDto;
    private List<SubjectEntity> subjects;


    @BeforeEach
    public void init() {

        subject = SubjectEntity.builder()
                .subjectId(1)
                .subjectName("English")
                .createDate(LocalDateTime.now())
                .activeStatus("Active")
                .build();

        subjects = new ArrayList<>();
        subjects.add(SubjectEntity.builder()
                .subjectId(1)
                .subjectName("English")
                .createDate(LocalDateTime.now())
                .activeStatus("Active")
                .build());
        subjects.add(SubjectEntity.builder()
                .subjectId(2)
                .subjectName("Science")
                .createDate(LocalDateTime.now())
                .activeStatus("Inactive")
                .build());

        subjectDto = new SubjectDto(
                0,
                "English",
                "Active");

    }

    @Test
    void SubjectServiceImplTest_createSubject_ReturnSavedSubject() {

        when(subjectRepository.save(any(SubjectEntity.class))).thenReturn(subject);

        SubjectDto savedSubject = subjectService.createSubject(subjectDto);

        Assertions.assertThat(savedSubject).isNotNull();
        Assertions.assertThat(savedSubject.subjectId()).isGreaterThan(0);
    }

    @Test
    void SubjectServiceImplTest_GetSubject_ReturnSubject() {
        int subjectId = 1;

        when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(subject));

        SubjectDto getSubject = subjectService.getSubject(subjectId);

        Assertions.assertThat(getSubject).isNotNull();
        Assertions.assertThat(getSubject.subjectName()).isEqualTo("English");
    }

    @Test
    void SubjectServiceImplTest_UpdateSubject_ReturnSubject() {
        int subjectId = 2;

        when(subjectRepository.save(any(SubjectEntity.class))).thenReturn(subjects.get(1));

        subjectDto = new SubjectDto(
                2,
                "Science",
                "Inactive");
        SubjectDto updatedSubject = subjectService.updateSubject(subjectId, subjectDto);

        Assertions.assertThat(updatedSubject).isNotNull();
        Assertions.assertThat(updatedSubject.activeStatus()).isEqualTo("Inactive");
    }

    @Test
    void SubjectServiceImplTest_DeleteSubject() {

        int subjectId = 1;

        assertThrows(ResponseStatusException.class, () -> subjectService.deleteSubject(subjectId));
    }

    @Test
    void SubjectServiceImplTest_GetAllSubjectsByActiveStatus_ReturnSubjects() {

        String activeStatus = "Active";

        when(subjectRepository.findAllByActiveStatus(activeStatus)).thenReturn(Collections.singletonList(subject));

        List<SubjectDto> getSubjects = subjectService.getAllSubjectsByActiveStatus(activeStatus);

        Assertions.assertThat(getSubjects).isNotNull();
        Assertions.assertThat(getSubjects.size()).isEqualTo(1);
    }

    @Test
    void SubjectServiceImplTest_GetAllSubjects_ReturnSubjects() {

        when(subjectRepository.findAll()).thenReturn(subjects);

        List<SubjectDto> getSubjects = subjectService.getAllSubjects();

        Assertions.assertThat(getSubjects).isNotNull();
        Assertions.assertThat(getSubjects.size()).isEqualTo(2);
        Assertions.assertThat(getSubjects.get(0).subjectName()).isEqualTo("English");
    }
}