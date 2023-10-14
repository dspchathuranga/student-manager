package com.cbrain.repository;

import com.cbrain.repository.entity.StudentEntity;
import com.cbrain.repository.entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {
    List<StudentEntity> findAllByActiveStatus(String activeStatus);

    List<StudentEntity> findBySubjectsContains(SubjectEntity subjectEntity);
}
