package com.cbrain.controller;


import com.cbrain.controller.dto.SubjectRequestDto;
import com.cbrain.controller.dto.SubjectResponseDto;
import com.cbrain.service.SubjectService;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = SubjectController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class SubjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubjectService subjectService;

    @Autowired
    private ObjectMapper objectMapper;
    private SubjectResponseDto subjectResponseDto;
    private SubjectRequestDto subjectRequestDto;
    private List<SubjectResponseDto> subjectResponseDtos;

    @BeforeEach
    public void init() {

        subjectRequestDto = new SubjectRequestDto(
                "English",
                "Active");

        subjectResponseDtos = new ArrayList<>();
        subjectResponseDtos.add(new SubjectResponseDto(
                1,
                "English",
                "Active"));
        subjectResponseDtos.add(new SubjectResponseDto(
                2,
                "Science",
                "Inactive"));

        subjectResponseDto = new SubjectResponseDto(
                1,
                "English",
                "Active");

    }

    @Test
    void SubjectControllerTest_CreateSubject_ReturnSavedSubject() throws Exception{
        when(subjectService.createSubject(subjectRequestDto)).thenReturn(subjectResponseDto);

        ResultActions response = mockMvc.perform(post("/api/v1/subject")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(subjectRequestDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.subjectName",
                        CoreMatchers.is(subjectResponseDto.subjectName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.activeStatus",
                        CoreMatchers.is(subjectResponseDto.activeStatus())));
    }

    @Test
    void SubjectControllerTest_GetSubject_ReturnSubject() throws Exception{
        int subjectId = 1;

        when(subjectService.getSubject(subjectId)).thenReturn(subjectResponseDtos.stream()
                .filter(studentDto1 -> studentDto1.subjectId() == subjectId)
                .toList().get(0));

        ResultActions response = mockMvc.perform(get("/api/v1/subject/"+subjectId)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.subjectName",
                        CoreMatchers.is(subjectResponseDto.subjectName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.activeStatus",
                        CoreMatchers.is(subjectResponseDto.activeStatus())));
    }

    @Test
    void SubjectControllerTest_GetAllSubjects_ReturnSubjects() throws Exception{
        when(subjectService.getAllSubjects()).thenReturn(subjectResponseDtos);

        ResultActions response = mockMvc.perform(get("/api/v1/subject/all")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string(objectMapper.writeValueAsString(subjectResponseDtos)));
    }

    @Test
    void SubjectControllerTest_GetAllSubjectsByActiveStatus_ReturnSubjects() throws Exception{
        String activeStatus = "Active";
        when(subjectService.getAllSubjectsByActiveStatus(activeStatus))
                .thenReturn(subjectResponseDtos.stream().filter(
                                subjectDto1 -> subjectDto1.activeStatus() == activeStatus)
                        .toList());

        ResultActions response = mockMvc.perform(get("/api/v1/subject/byStatus")
                .contentType(MediaType.APPLICATION_JSON)
                .param("activeStatus", activeStatus));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string(objectMapper.writeValueAsString(subjectResponseDtos.stream().filter(
                                        subjectDto1 -> subjectDto1.activeStatus() == activeStatus)
                                .toList())));
    }

    @Test
    void SubjectControllerTest_UpdateSubject_ReturnSubject() throws Exception{
        int subjectId = 1;

        when(subjectService.updateSubject(subjectId,subjectRequestDto)).thenReturn(subjectResponseDto);

        ResultActions response = mockMvc.perform(put("/api/v1/subject/"+subjectId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(subjectRequestDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string(objectMapper.writeValueAsString(subjectResponseDto)));
    }

    @Test
    void SubjectControllerTest_DeleteSubject() throws Exception{
        int subjectId = 1;
        doNothing().when(subjectService).deleteSubject(subjectId);

        ResultActions response = mockMvc.perform(delete("/api/v1/subject/"+subjectId)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}