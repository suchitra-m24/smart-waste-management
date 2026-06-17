package com.smartwaste.smart_waste_backend.model;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "rewards")
public class Reward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private int pointsRequired;

    private int quantity;

    private boolean active = true;

}
