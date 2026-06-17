package com.smartwaste.smart_waste_backend.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "redemptions")
public class Redemption {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false)
        @JsonIgnoreProperties({"password"})
        private User user;

        @ManyToOne
        @JoinColumn(name = "reward_id", nullable = false)
        private Reward reward;

        private int pointsSpent;

        private LocalDateTime redeemedAt = LocalDateTime.now();
    }

