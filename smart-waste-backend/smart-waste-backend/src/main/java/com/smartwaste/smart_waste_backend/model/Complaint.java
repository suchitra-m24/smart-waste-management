package com.smartwaste.smart_waste_backend.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "complaints")
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String photoUrl;
    private String address;

    @Column(precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(precision = 11, scale = 8)
    private BigDecimal longitude;;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.REPORTED;

    @ManyToOne
    @JoinColumn(name = "citizen_id", nullable = false)
    @JsonIgnoreProperties({"password", "ecoPoints"})
    private User citizen;

    @ManyToOne
    @JoinColumn(name = "collector_id")
    @JsonIgnoreProperties({"password", "ecoPoints"})
    private User collector;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime collectedAt;

    public enum Status {
        REPORTED, ASSIGNED, IN_PROGRESS, COLLECTED
    }

}
