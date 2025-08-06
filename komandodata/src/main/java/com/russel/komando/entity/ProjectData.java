package com.russel.komando.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "Project")
public class ProjectData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private int projectId;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "project_start_date")
    private LocalDate projectStartDate;

    @Column(name = "project_due_date")
    private LocalDate projectDueDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "MM dd, yyyy - hh:mm a", timezone = "GMT+08:00")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "MM dd, yyyy - hh:mm a", timezone = "GMT+08:00")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Relationships ======================================= //

    @ManyToOne
    @JoinColumn(name = "status_id")
    private StatusData status;

    @OneToMany(mappedBy = "project")
    private List<TaskData> tasks;

    @OneToMany(mappedBy = "project")
    private List<ProjectAssignmentData> projectAssignments;
}
