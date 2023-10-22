package com.cbrain.controller;

import com.cbrain.controller.dto.SubjectRequestDto;
import com.cbrain.controller.dto.SubjectResponseDto;
import com.cbrain.service.SubjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/subject")
@Tag(name = "Subject")
public class SubjectController {

    @Autowired
    private final SubjectService subjectService;

    @Operation(operationId = "addSubject", summary = "Add Subject", tags = { "Subject" },
            description = "This API for addSubject and it return saved Subject DTO.",
            parameters = { @Parameter(in = ParameterIn.PATH, name = "subjectDto", description = "Subject DTO")},
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation"),
                    @ApiResponse(responseCode = "400", description = "Invalid Subject DTO supplied"),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials / Token not found"),
                    @ApiResponse(responseCode = "403", description = "Invalid permissions") })
    @PostMapping
    public SubjectResponseDto createSubject(@RequestBody SubjectRequestDto subjectRequestDto) {
        return subjectService.createSubject(subjectRequestDto);
    }

    @Operation(operationId = "getSubjectBySubjectId", summary = "Get Subject by subjectId", tags = { "Subject" },
            description = "This API for getSubjectBySubjectId (primary key) and it return Subject DTO",
            parameters = { @Parameter(in = ParameterIn.PATH, name = "subjectId", description = "Subject Id") },
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation"),
                    @ApiResponse(responseCode = "400", description = "Invalid Subject ID supplied"),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials / Token not found"),
                    @ApiResponse(responseCode = "403", description = "Invalid permissions"),
                    @ApiResponse(responseCode = "404", description = "Subject not found") })
    @GetMapping("/{subjectId}")
    public SubjectResponseDto getSubject(@PathVariable Integer subjectId) {
        return subjectService.getSubject(subjectId);
    }

    @Operation(operationId = "getAllSubjects", summary = "Get All Subjects", tags = { "Subject" },
            description = "This API for getAllSubjects  and it return List of Subject DTO's",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation"),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials / Token not found"),
                    @ApiResponse(responseCode = "403", description = "Invalid permissions"),
                    @ApiResponse(responseCode = "404", description = "Subject not found") })
    @GetMapping("/all")
    public List<SubjectResponseDto> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @Operation(operationId = "getAllSubjectsByActiveStatus", summary = "Get All Subjects by activeStatus", tags = { "Subject" },
            description = "This API for getAllSubjectsByActiveStatus and it return List Subject DTO's",
            parameters = { @Parameter(in = ParameterIn.PATH, name = "activeStatus", description = "Active Status") },
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation"),
                    @ApiResponse(responseCode = "400", description = "Invalid Active Status supplied"),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials / Token not found"),
                    @ApiResponse(responseCode = "403", description = "Invalid permissions"),
                    @ApiResponse(responseCode = "404", description = "Subject not found") })
    @GetMapping("/byStatus")
    public List<SubjectResponseDto> getAllSubjectsByActiveStatus(@RequestParam String activeStatus) {
        return subjectService.getAllSubjectsByActiveStatus(activeStatus);
    }

    @Operation(operationId = "updateSubjectBySubjectId", summary = "Update Subject by subjectId", tags = { "Subject" },
            description = "This API for updateSubjectBySubjectId and it return updated Subject DTO.",
            parameters = { @Parameter(in = ParameterIn.PATH, name = "subjectId", description = "Subject Id"),
                    @Parameter(in = ParameterIn.PATH, name = "subjectDto", description = "Subject DTO")},
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation"),
                    @ApiResponse(responseCode = "400", description = "Invalid Subject Id / Subject DTO supplied"),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials / Token not found"),
                    @ApiResponse(responseCode = "403", description = "Invalid permissions"),
                    @ApiResponse(responseCode = "404", description = "Subject not found") })
    @PutMapping("/{subjectId}")
    public SubjectResponseDto updateSubject(@PathVariable Integer subjectId, @RequestBody SubjectRequestDto subjectRequestDto) {
        return subjectService.updateSubject(subjectId, subjectRequestDto);
    }

    @Operation(operationId = "deleteSubjectBySubjectId", summary = "Delete Subject by subjectId", tags = { "Subject" },
            description = "This API for deleteSubjectBySubjectId.",
            parameters = { @Parameter(in = ParameterIn.PATH, name = "subjectId", description = "Subject Id")},
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation"),
                    @ApiResponse(responseCode = "400", description = "Invalid Subject Id supplied"),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials / Token not found"),
                    @ApiResponse(responseCode = "403", description = "Invalid permissions"),
                    @ApiResponse(responseCode = "404", description = "Subject not found") })
    @DeleteMapping("/{subjectId}")
    public void deleteSubject(@PathVariable Integer subjectId) {
        subjectService.deleteSubject(subjectId);
    }
}
