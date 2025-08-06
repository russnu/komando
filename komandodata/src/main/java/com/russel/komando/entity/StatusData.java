package com.russel.komando.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "Status")
public class StatusData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    private int statusId;

    @Column(name = "status_title", unique = true, nullable = false)
    private String statusTitle;

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

    @OneToMany(mappedBy = "status")
    private List<ProjectData> projects;

    @OneToMany(mappedBy = "status")
    private List<TaskData> tasks;


}
