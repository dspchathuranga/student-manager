package com.cbrain.controller;

import com.cbrain.controller.dto.StudentRequestDto;
import com.cbrain.controller.dto.StudentResponseDto;
import com.cbrain.controller.dto.SubjectResponseDto;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
    private StudentResponseDto studentResponseDto;

    private StudentRequestDto studentRequestDto;
    private List<StudentResponseDto> studentResponseDtos;
    private List<SubjectResponseDto> subjectResponseDtos;

    private List<Integer> subjectIds;

    @BeforeEach
    public void init() {

        subjectResponseDtos = new ArrayList<>();
        subjectResponseDtos.add(new SubjectResponseDto(
                1,
                "English",
                "Active"));

        subjectResponseDtos.add(new SubjectResponseDto(
                2,
                "Science",
                "Inactive"));

        subjectIds = new ArrayList<>();
        subjectIds.add(1);
        subjectIds.add(2);

        studentRequestDto = new StudentRequestDto("DSP",
                "Chathuranga", 33,
                "Active", subjectIds);

        studentResponseDto = new StudentResponseDto( 1,
                "DSP",
                "Chathuranga", 33,
                "Active", subjectResponseDtos
        );

        studentResponseDtos = new ArrayList<>();
        studentResponseDtos.add(new StudentResponseDto(1, "DSP",
                "Chathuranga", 33,
                "Active", subjectResponseDtos));
        studentResponseDtos.add(new StudentResponseDto(2, "Jayani",
                "Salgado", 21,
                "Inactive", subjectResponseDtos));

    }

    @Test
    void StudentControllerTest_CreateStudent_ReturnSavedStudent() throws Exception{
        when(studentService.createStudent(studentRequestDto)).thenReturn(studentResponseDto);

        ResultActions response = mockMvc.perform(post("/api/v1/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentRequestDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.studentFirstName",
                        CoreMatchers.is(studentResponseDto.studentFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.studentAge",
                        CoreMatchers.is(studentResponseDto.studentAge())));
    }

    @Test
    void StudentControllerTest_GetStudent() throws Exception{
        int studentId = 1;

        when(studentService.getStudent(studentId)).thenReturn(studentResponseDtos.stream()
                .filter(studentDto1 -> studentDto1.studentId() == studentId)
                .toList().get(0));

        ResultActions response = mockMvc.perform(get("/api/v1/student/"+studentId)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.studentFirstName",
                        CoreMatchers.is(studentResponseDto.studentFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.studentAge",
                        CoreMatchers.is(studentResponseDto.studentAge())));
    }

    @Test
    void StudentControllerTest_GetAllStudents_ReturnStudents() throws Exception {

        when(studentService.getAllStudents()).thenReturn(studentResponseDtos);

        ResultActions response = mockMvc.perform(get("/api/v1/student/all")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string(objectMapper.writeValueAsString(studentResponseDtos)));
    }

    @Test
    void StudentControllerTest_GetAllStudentsByActiveStatus_ReturnStudents() throws Exception {
        String activeStatus = "Active";
        when(studentService.getAllStudentsByActiveStatus(activeStatus))
                .thenReturn(studentResponseDtos.stream().filter(
                        studentDto1 -> studentDto1.activeStatus() == activeStatus)
                        .toList());

        ResultActions response = mockMvc.perform(get("/api/v1/student/byStatus")
                .contentType(MediaType.APPLICATION_JSON)
                .param("activeStatus", activeStatus));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string(objectMapper.writeValueAsString(studentResponseDtos.stream().filter(
                                        studentDto1 -> studentDto1.activeStatus() == activeStatus)
                                .toList())));
    }

    @Test
    void StudentControllerTest_UpdateStudent_ReturnStudent() throws Exception {
        int studentId = 1;

        when(studentService.updateStudent(studentId,studentRequestDto)).thenReturn(studentResponseDto);

        ResultActions response = mockMvc.perform(put("/api/v1/student/"+studentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentRequestDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string(objectMapper.writeValueAsString(studentResponseDto)));
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