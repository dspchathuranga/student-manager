package com.cbrain.service;

import com.cbrain.controller.dto.StudentDto;
import com.cbrain.repository.StudentRepository;
import com.cbrain.repository.entity.StudentEntity;
import com.cbrain.repository.entity.SubjectEntity;
import com.cbrain.service.impl.StudentServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    private StudentEntity student;
    private StudentDto studentDto;
    private List<StudentEntity> students;

    private List<SubjectEntity> subjects;

    @BeforeEach
    public void init() {
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

        student = StudentEntity.builder()
                .studentId(1)
                .studentFirstName("DSP")
                .studentLastName("Chathuranga")
                .studentAge(33)
                .activeStatus("Active")
                .createDate(LocalDateTime.now())
                .subjects(subjects)
                .build();

        studentDto = new StudentDto(0, "DSP",
                "Chathuranga", 33,
                "Active", new ArrayList<>());

        students = new ArrayList<>();
        students.add(StudentEntity.builder()
                .studentId(1)
                .studentFirstName("DSP")
                .studentLastName("Chathuranga")
                .studentAge(33)
                .activeStatus("Active")
                .createDate(LocalDateTime.now())
                .subjects(subjects)
                .build());
        students.add(StudentEntity.builder()
                .studentId(2)
                .studentFirstName("Jayani")
                .studentLastName("Salgado")
                .studentAge(31)
                .activeStatus("Inactive")
                .createDate(LocalDateTime.now())
                .subjects(subjects)
                .build());

    }
    @Test
    void StudentServiceImpl_CreateStudent_ReturnStudentDto() {
        StudentEntity student = StudentEntity.builder()
                .studentId(1)
                .studentFirstName("DSP")
                .studentLastName("Chathuranga")
                .studentAge(33)
                .activeStatus("Active")
                .createDate(LocalDateTime.now())
                .subjects(new ArrayList<>())
                .build();

        StudentDto studentDto = new StudentDto(0, "DSP",
                "Chathuranga", 33,
                "Active", new ArrayList<>());

        when(studentRepository.save(any(StudentEntity.class))).thenReturn(student);

        StudentDto savedStudent = studentService.createStudent(studentDto);

        Assertions.assertThat(savedStudent).isNotNull();
        Assertions.assertThat(savedStudent.studentId()).isGreaterThan(0);
    }

    @Test
    void StudentServiceImpl_GrtStudnet_ReturnStudent() {
        int studentId = 1;

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        StudentDto getStudent = studentService.getStudent(studentId);

        Assertions.assertThat(getStudent).isNotNull();
        Assertions.assertThat(getStudent.studentFirstName()).isEqualTo("DSP");
    }

    @Test
    void StudentServiceImpl_UpdateStudent_ReturnStudent() {
        int studentId = 2;

        student.setActiveStatus("Inactive");
        when(studentRepository.save(any(StudentEntity.class))).thenReturn(student);

        studentDto = new StudentDto(0, "DSP",
                "Chathuranga", 33,
                "Inactive", new ArrayList<>());
        StudentDto updatedStudent = studentService.updateStudent(studentId, studentDto);

        Assertions.assertThat(updatedStudent).isNotNull();
        Assertions.assertThat(updatedStudent.activeStatus()).isEqualTo("Inactive");
    }

    @Test
    void StudentServiceImpl_DeleteStudent() {
        int studentId = 1;

        studentService.deleteStudent(studentId);

        verify(studentRepository).deleteById(studentId);
    }

    @Test
    void StudentServiceImpl_GetAllStudents_ReturnStudentDtos() {

        when(studentRepository.findAll()).thenReturn(students);

        List<StudentDto> getStudents = studentService.getAllStudents();

        Assertions.assertThat(getStudents).isNotNull();
        Assertions.assertThat(getStudents.size()).isEqualTo(2);
        Assertions.assertThat(getStudents.get(0).studentSubjects().size()).isEqualTo(2);
    }

    @Test
    void StudentServiceImpl_GetAllStudentsByActiveStatus_ReturnStudents() {

        String activeStatus = "Active";

        when(studentRepository.findAllByActiveStatus(activeStatus)).thenReturn(Collections.singletonList(student));

        List<StudentDto> getSubjects = studentService.getAllStudentsByActiveStatus(activeStatus);

        Assertions.assertThat(getSubjects).isNotNull();
        Assertions.assertThat(getSubjects.size()).isEqualTo(1);
    }
}