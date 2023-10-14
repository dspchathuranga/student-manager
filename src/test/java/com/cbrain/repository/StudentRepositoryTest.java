package com.cbrain.repository;


import com.cbrain.controller.dto.StudentDto;
import com.cbrain.controller.dto.SubjectDto;
import com.cbrain.repository.entity.StudentEntity;
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
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    private StudentEntity student;
    private List<StudentEntity> students;

    @BeforeEach
    public void init() {
        student = StudentEntity.builder()
                .studentFirstName("DSP")
                .studentLastName("Chathuranga")
                .studentAge(33)
                .activeStatus("Active")
                .createDate(LocalDateTime.now())
                .build();

        students = new ArrayList<>();
        students.add(StudentEntity.builder()
                .studentFirstName("DSP")
                .studentLastName("Chathuranga")
                .studentAge(33)
                .activeStatus("Active")
                .createDate(LocalDateTime.now())
                .build());
        students.add(StudentEntity.builder()
                .studentFirstName("Jayani")
                .studentLastName("Salgado")
                .studentAge(31)
                .activeStatus("Inactive")
                .createDate(LocalDateTime.now())
                .build());

    }

    @Test
    public void StudentRepository_Save_ReturnSavedStudent(){

        StudentEntity savedStudent = studentRepository.save(student);

        //Assert
        Assertions.assertThat(savedStudent).isNotNull();
        Assertions.assertThat(savedStudent.getStudentId()).isGreaterThan(0);
    }

    @Test
    public void StudentRepository_SaveAll_ReturnSavedStudents(){

        List<StudentEntity> savedStudents = studentRepository.saveAll(students);

        Assertions.assertThat(savedStudents).isNotNull();
        Assertions.assertThat(savedStudents.size()).isEqualTo(2);
    }

    @Test
    public void StudentRepository_FindAll_ReturnAllStudents(){

        List<StudentEntity> savedStudents = studentRepository.saveAll(students);
        List<StudentEntity> getStudents = studentRepository.findAll();

        Assertions.assertThat(savedStudents).isNotNull();
        Assertions.assertThat(savedStudents.size()).isEqualTo(2);

        Assertions.assertThat(getStudents).isNotNull();
        Assertions.assertThat(getStudents.size()).isEqualTo(2);
    }

    @Test
    public void StudentRepository_FindById_ReturnStudent(){

        StudentEntity savedStudent = studentRepository.save(student);
        StudentEntity getStudent = studentRepository.findById(savedStudent.getStudentId()).orElse(null);

        Assertions.assertThat(savedStudent).isNotNull();
        Assertions.assertThat(savedStudent.getStudentId()).isGreaterThan(0);

        Assertions.assertThat(getStudent).isNotNull();
        Assertions.assertThat(getStudent.getStudentId()).isEqualTo(savedStudent.getStudentId());
        Assertions.assertThat(getStudent.getStudentFirstName()).isEqualTo("DSP");
    }

    @Test
    public void StudentRepository_FindAllByActiveStatus_ReturnStudents(){

        List<StudentEntity> savedStudents = studentRepository.saveAll(students);
        List<StudentEntity> getStudents = studentRepository.findAllByActiveStatus("Active");

        //Assert
        Assertions.assertThat(savedStudents).isNotNull();
        Assertions.assertThat(savedStudents.size()).isEqualTo(2);

        Assertions.assertThat(getStudents).isNotNull();
        Assertions.assertThat(getStudents.size()).isEqualTo(1);
    }

    @Test
    public void StudentRepository_UpdateStudent_ReturnStudent(){

        List<StudentEntity> savedStudents = studentRepository.saveAll(students);
        int studentId= savedStudents.get(0).getStudentId();
        StudentEntity getStudent = studentRepository.findById(studentId).orElse(null);
        getStudent.setActiveStatus("Inactive");
        StudentEntity updatedStudent = studentRepository.save(getStudent);

        Assertions.assertThat(savedStudents).isNotNull();
        Assertions.assertThat(savedStudents.size()).isEqualTo(2);

        Assertions.assertThat(getStudent).isNotNull();
        Assertions.assertThat(getStudent.getStudentId()).isEqualTo(studentId);

        Assertions.assertThat(updatedStudent).isNotNull();
        Assertions.assertThat(updatedStudent.getActiveStatus()).isEqualTo("Inactive");
    }


    @Test
    public void StudentRepository_DeleteStudentById_ReturnOtherStudents(){

        List<StudentEntity> savedStudents = studentRepository.saveAll(students);
        studentRepository.deleteById(savedStudents.get(1).getStudentId());
        List<StudentEntity> getStudents = studentRepository.findAll();

        Assertions.assertThat(savedStudents).isNotNull();
        Assertions.assertThat(savedStudents.size()).isEqualTo(2);

        Assertions.assertThat(getStudents).isNotNull();
        Assertions.assertThat(getStudents.size()).isEqualTo(1);
    }

    @Test
    public void StudentRepository_DeleteAllStudents_ReturnEmptyList(){

        List<StudentEntity> savedStudents = studentRepository.saveAll(students);
        studentRepository.deleteAll();
        List<StudentEntity> getStudents = studentRepository.findAll();

        Assertions.assertThat(savedStudents).isNotNull();
        Assertions.assertThat(savedStudents.size()).isEqualTo(2);

        Assertions.assertThat(getStudents).isNotNull();
        Assertions.assertThat(getStudents).isEmpty();
    }

}