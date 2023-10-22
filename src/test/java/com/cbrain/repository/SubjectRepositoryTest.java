package com.cbrain.repository;

import com.cbrain.repository.entity.SubjectEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class SubjectRepositoryTest {

    @Autowired
    private SubjectRepository subjectRepository;

    private SubjectEntity subject;
    private List<SubjectEntity> subjects;


    @BeforeEach
    public void init() {

        subject = SubjectEntity.builder()
                .subjectName("English")
                .createDate(LocalDateTime.now())
                .activeStatus("Active")
                .build();

        subjects = new ArrayList<>();
        subjects.add(SubjectEntity.builder()
                .subjectName("English")
                .createDate(LocalDateTime.now())
                .activeStatus("Active")
                .build());
        subjects.add(SubjectEntity.builder()
                .subjectName("Science")
                .createDate(LocalDateTime.now())
                .activeStatus("Inactive")
                .build());

    }

    @Test
    public void SubjectRepository_Save_ReturnSavedSubject(){

        SubjectEntity savedSubject = subjectRepository.save(subject);

        Assertions.assertThat(savedSubject).isNotNull();
        Assertions.assertThat(savedSubject.getSubjectId()).isGreaterThan(0);

    }
    @Test
    public void SubjectRepository_SaveAll_ReturnSavedSubjects(){

        List<SubjectEntity> savedSubjects = subjectRepository.saveAll(subjects);

        Assertions.assertThat(savedSubjects).isNotNull();
        Assertions.assertThat(savedSubjects.size()).isEqualTo(2);

    }

    @Test
    public void SubjectRepository_FindAll_ReturnSubjects(){

        List<SubjectEntity> savedSubjects = subjectRepository.saveAll(subjects);
        List<SubjectEntity> getSubjects = subjectRepository.findAll();

        Assertions.assertThat(savedSubjects).isNotNull();
        Assertions.assertThat(savedSubjects.size()).isEqualTo(2);

        Assertions.assertThat(getSubjects).isNotNull();
        Assertions.assertThat(getSubjects.size()).isEqualTo(2);

    }

    @Test
    public void SubjectRepository_FindById_ReturnSubject(){

        List<SubjectEntity> savedSubjects = subjectRepository.saveAll(subjects);
        SubjectEntity getSubject = subjectRepository.findById(savedSubjects.get(0).getSubjectId()).orElse(null);

        Assertions.assertThat(savedSubjects).isNotNull();
        Assertions.assertThat(savedSubjects.size()).isEqualTo(2);

        Assertions.assertThat(getSubject).isNotNull();
        Assertions.assertThat(getSubject.getSubjectName()).isEqualTo("English");

    }

    @Test
    public void SubjectRepository_FindAllByActiveStatus_ReturnSubjects(){

        List<SubjectEntity> savedSubjects = subjectRepository.saveAll(subjects);
        List<SubjectEntity> getSubjects = subjectRepository.findAllByActiveStatus("Active");

        Assertions.assertThat(savedSubjects).isNotNull();
        Assertions.assertThat(savedSubjects.size()).isEqualTo(2);

        Assertions.assertThat(getSubjects).isNotNull();
        Assertions.assertThat(getSubjects.size()).isEqualTo(1);

    }

    @Test
    public void SubjectRepository_UpdateSubject_ReturnSubject(){

        List<SubjectEntity> savedSubjects = subjectRepository.saveAll(subjects);
        int subjectId = savedSubjects.get(0).getSubjectId();
        SubjectEntity getSubject = subjectRepository.findById(subjectId).orElse(null);
        getSubject.setActiveStatus("Inactive");
        SubjectEntity updatedSubject = subjectRepository.save(getSubject);

        Assertions.assertThat(savedSubjects).isNotNull();
        Assertions.assertThat(savedSubjects.size()).isEqualTo(2);

        Assertions.assertThat(getSubject).isNotNull();
        Assertions.assertThat(getSubject.getSubjectId()).isEqualTo(subjectId);

        Assertions.assertThat(updatedSubject).isNotNull();
        Assertions.assertThat(updatedSubject.getActiveStatus()).isEqualTo("Inactive");

    }

    @Test
    public void SubjectRepository_DeleteSubjectById_ReturnOtherSubjects(){

        List<SubjectEntity> savedSubjects = subjectRepository.saveAll(subjects);
        subjectRepository.deleteById(savedSubjects.get(0).getSubjectId());
        List<SubjectEntity> getSubjects = subjectRepository.findAll();

        Assertions.assertThat(savedSubjects).isNotNull();
        Assertions.assertThat(savedSubjects.size()).isEqualTo(2);

        Assertions.assertThat(getSubjects).isNotNull();
        Assertions.assertThat(getSubjects.size()).isEqualTo(1);

    }

    @Test
    public void SubjectRepository_DeleteAllSubjects_ReturnEmptyList(){

        List<SubjectEntity> savedSubjects = subjectRepository.saveAll(subjects);
        subjectRepository.deleteAll();
        List<SubjectEntity> getSubjects = subjectRepository.findAll();

        Assertions.assertThat(savedSubjects).isNotNull();
        Assertions.assertThat(savedSubjects.size()).isEqualTo(2);

        Assertions.assertThat(getSubjects).isNotNull();
        Assertions.assertThat(getSubjects).isEmpty();

    }

}