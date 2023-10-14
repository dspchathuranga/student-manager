package com.cbrain.controller;

import com.cbrain.controller.dto.StudentDto;
import com.cbrain.controller.dto.SubjectDto;
import com.cbrain.repository.entity.StudentEntity;
import com.cbrain.repository.entity.SubjectEntity;
import com.cbrain.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = StudentController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;
    private StudentDto studentDto;
    private List<StudentDto> studentDtos;
    private List<SubjectDto> subjectDtos;

    @BeforeEach
    public void init() {

        subjectDtos = new ArrayList<>();
        subjectDtos.add(new SubjectDto(
                1,
                "English",
                "Active"));

        subjectDtos.add(new SubjectDto(
                2,
                "Science",
                "Inactive"));

        studentDto = new StudentDto(0, "DSP",
                "Chathuranga", 33,
                "Active", subjectDtos);

        studentDtos = new ArrayList<>();
        studentDtos.add(new StudentDto(1, "DSP",
                "Chathuranga", 33,
                "Active", subjectDtos));
        studentDtos.add(new StudentDto(2, "Jayani",
                "Salgado", 21,
                "Inactive", subjectDtos));

    }

    @Test
    void StudentControllerTest_CreateStudent_ReturnSavedStudent() throws Exception{
        given(studentService.createStudent(ArgumentMatchers.any()))
                .willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/api/v1/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.studentFirstName",
                        CoreMatchers.is(studentDto.studentFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.studentAge",
                        CoreMatchers.is(studentDto.studentAge())));
    }

    @Test
    void StudentControllerTest_GetStudent() throws Exception{
        int studentId = 1;

        when(studentService.getStudent(studentId)).thenReturn(studentDtos.stream()
                .filter(studentDto1 -> studentDto1.studentId() == studentId)
                .toList().get(0));

        ResultActions response = mockMvc.perform(get("/api/v1/student/"+studentId)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.studentFirstName",
                        CoreMatchers.is(studentDto.studentFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.studentAge",
                        CoreMatchers.is(studentDto.studentAge())));
    }

    @Test
    void StudentControllerTest_GetAllStudents_ReturnStudents() throws Exception {

        when(studentService.getAllStudents()).thenReturn(studentDtos);

        ResultActions response = mockMvc.perform(get("/api/v1/student/all")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string(objectMapper.writeValueAsString(studentDtos)));
    }

    @Test
    void StudentControllerTest_GetAllStudentsByActiveStatus_ReturnStudents() throws Exception {
        String activeStatus = "Active";
        when(studentService.getAllStudentsByActiveStatus(activeStatus))
                .thenReturn(studentDtos.stream().filter(
                        studentDto1 -> studentDto1.activeStatus() == activeStatus)
                        .toList());

        ResultActions response = mockMvc.perform(get("/api/v1/student/byStatus")
                .contentType(MediaType.APPLICATION_JSON)
                .param("activeStatus", activeStatus));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string(objectMapper.writeValueAsString(studentDtos.stream().filter(
                                        studentDto1 -> studentDto1.activeStatus() == activeStatus)
                                .toList())));
    }

    @Test
    void StudentControllerTest_UpdateStudent_ReturnStudent() throws Exception {
        int studentId = 1;

        when(studentService.updateStudent(studentId,studentDto)).thenReturn(studentDto);

        ResultActions response = mockMvc.perform(put("/api/v1/student/"+studentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string(objectMapper.writeValueAsString(studentDto)));
    }

    @Test
    void StudentControllerTest_DeleteStudent() throws Exception {
        int studentId = 1;
        doNothing().when(studentService).deleteStudent(studentId);

        ResultActions response = mockMvc.perform(delete("/api/v1/student/"+studentId)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

}