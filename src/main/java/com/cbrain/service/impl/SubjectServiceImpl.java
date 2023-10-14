package com.cbrain.service.impl;

import com.cbrain.controller.dto.SubjectDto;
import com.cbrain.repository.StudentRepository;
import com.cbrain.repository.SubjectRepository;
import com.cbrain.repository.entity.StudentEntity;
import com.cbrain.repository.entity.SubjectEntity;
import com.cbrain.service.SubjectService;
import com.cbrain.utils.ObjectMapperUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    private final StudentRepository studentRepository;

    @Override
    public SubjectDto createSubject(SubjectDto subjectDto) {
        return ObjectMapperUtility.mapToSubjectDto(
                subjectRepository.save(
                    ObjectMapperUtility.mapToSubjectEntity(subjectDto)
                )
        );
    }

    @Override
    public SubjectDto getSubject(Integer subjectId) {
        return ObjectMapperUtility.mapToSubjectDto(
                subjectRepository.findById(subjectId).get()
        );
    }

    @Override
    public SubjectDto updateSubject(Integer subjectId, SubjectDto subjectDto) {
        return ObjectMapperUtility.mapToSubjectDto(
                subjectRepository.save(
                        ObjectMapperUtility.mapToSubjectEntity(subjectId,subjectDto)
                )
        );
    }

    @Override
    public void deleteSubject(Integer subjectId) {

        SubjectEntity subject = subjectRepository.findById(subjectId).orElse(null);
        if (subject != null) {
            List<StudentEntity> studentsToDelete = studentRepository.findBySubjectsContains(subject);

            for (StudentEntity student : studentsToDelete) {
                student.getSubjects().remove(subject); // Remove the subject association
                studentRepository.save(student);
            }

            subjectRepository.delete(subject);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Subject not found");
        }

    }

    @Override
    public List<SubjectDto> getAllSubjectsByActiveStatus(String activeStatus) {
        return subjectRepository.findAllByActiveStatus(activeStatus).stream().map(
                subjectEntity -> ObjectMapperUtility.mapToSubjectDto(subjectEntity)
        ).toList();
    }

    @Override
    public List<SubjectDto> getAllSubjects() {
        return subjectRepository.findAll().stream().map(
                subjectEntity -> ObjectMapperUtility.mapToSubjectDto(subjectEntity)
        ).toList();
    }
}
