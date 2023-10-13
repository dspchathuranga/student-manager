package com.cbrain.repository.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "subject")
public class SubjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Integer subjectId;

    @Column(name = "subject_name")
    private String subjectName;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "active_status")
    private String activeStatus;

    @ManyToMany(mappedBy = "subjects")
    private List<StudentEntity> students;

}
