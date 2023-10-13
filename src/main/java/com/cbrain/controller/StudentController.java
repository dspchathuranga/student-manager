package com.cbrain.controller;

import com.cbrain.controller.dto.StudentDto;
import com.cbrain.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/student")
@Tag(name = "Student")
public class StudentController {

    private final StudentService studentService;

    @Operation(operationId = "addStudent", summary = "Add Student", tags = { "Student" },
            description = "This API for addStudent and it return saved Student DTO.",
            parameters = { @Parameter(in = ParameterIn.PATH, name = "studentDto", description = "Student DTO")},
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation"),
                    @ApiResponse(responseCode = "400", description = "Invalid Student DTO supplied"),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials / Token not found"),
                    @ApiResponse(responseCode = "403", description = "Invalid permissions") })
    @PostMapping
    public StudentDto createStudent(@RequestBody StudentDto studentDto) {
        return studentService.createStudent(studentDto);
    }

    @Operation(operationId = "getStudentByStudentId", summary = "Get Student by studentId", tags = { "Student" },
            description = "This API for getStudentByStudentId (primary key) and it return Student DTO",
            parameters = { @Parameter(in = ParameterIn.PATH, name = "studentId", description = "Student Id") },
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation"),
                    @ApiResponse(responseCode = "400", description = "Invalid Student ID supplied"),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials / Token not found"),
                    @ApiResponse(responseCode = "403", description = "Invalid permissions"),
                    @ApiResponse(responseCode = "404", description = "Student not found") })
    @GetMapping("/{studentId}")
    public StudentDto getStudent(@PathVariable Integer studentId) {
        return studentService.getStudent(studentId);
    }

    @Operation(operationId = "getAllStudents", summary = "Get All Students", tags = { "Student" },
            description = "This API for getAllStudents  and it return List of Student DTO's",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation"),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials / Token not found"),
                    @ApiResponse(responseCode = "403", description = "Invalid permissions"),
                    @ApiResponse(responseCode = "404", description = "Students not found") })
    @GetMapping("/all")
    public List<StudentDto> getAllStudents() {
        return studentService.getAllStudents();
    }

    @Operation(operationId = "getAllStudentsByActiveStatus", summary = "Get All Students by activeStatus", tags = { "Student" },
            description = "This API for getAllStudentsByActiveStatus and it return List Student DTO's",
            parameters = { @Parameter(in = ParameterIn.PATH, name = "activeStatus", description = "Active Status") },
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation"),
                    @ApiResponse(responseCode = "400", description = "Invalid Active Status supplied"),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials / Token not found"),
                    @ApiResponse(responseCode = "403", description = "Invalid permissions"),
                    @ApiResponse(responseCode = "404", description = "Students not found") })
    @GetMapping("/byStatus")
    public List<StudentDto> getAllStudentsByActiveStatus(@RequestParam String activeStatus) {
        return studentService.getAllStudentsByActiveStatus(activeStatus);
    }

    @Operation(operationId = "updateStudentByStudentId", summary = "Update Student by studentId", tags = { "Student" },
            description = "This API for updateStudentByStudentId and it return updated Student DTO.",
            parameters = { @Parameter(in = ParameterIn.PATH, name = "studentId", description = "Student Id"),
                    @Parameter(in = ParameterIn.PATH, name = "studentDto", description = "Student DTO")},
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation"),
                    @ApiResponse(responseCode = "400", description = "Invalid Student Id / Student DTO supplied"),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials / Token not found"),
                    @ApiResponse(responseCode = "403", description = "Invalid permissions"),
                    @ApiResponse(responseCode = "404", description = "Student not found") })
    @PutMapping("/{studentId}")
    public StudentDto updateStudent(@PathVariable Integer studentId, @RequestBody StudentDto studentDto) {
        return studentService.updateStudent(studentId, studentDto);
    }

    @Operation(operationId = "deleteStudentByStudentId", summary = "Delete Student by studentId", tags = { "Student" },
            description = "This API for deleteStudentByStudentId.",
            parameters = { @Parameter(in = ParameterIn.PATH, name = "studentId", description = "Student Id")},
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation"),
                    @ApiResponse(responseCode = "400", description = "Invalid Student Id supplied"),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials / Token not found"),
                    @ApiResponse(responseCode = "403", description = "Invalid permissions"),
                    @ApiResponse(responseCode = "404", description = "Student not found") })
    @DeleteMapping("/{studentId}")
    public void deleteStudent(@PathVariable Integer studentId) {
        studentService.deleteStudent(studentId);
    }
}
