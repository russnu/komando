package com.russel.komando.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.russel.komando.enums.Priority;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "Task")
public class TaskData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private int taskId;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private Priority priority;

    @Column(name = "task_due_date")
    private LocalDate taskDueDate;

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

    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectData project;

    @OneToMany(mappedBy = "task")
    private List<TaskAssignmentData> taskAssignments;


}
