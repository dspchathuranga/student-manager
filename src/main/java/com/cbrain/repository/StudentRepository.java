package com.cbrain.repository;

import com.cbrain.repository.entity.StudentEntity;
import com.cbrain.repository.entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {
    List<StudentEntity> findAllByActiveStatus(String activeStatus);

    List<StudentEntity> findBySubjectsContains(SubjectEntity subjectEntity);
}
