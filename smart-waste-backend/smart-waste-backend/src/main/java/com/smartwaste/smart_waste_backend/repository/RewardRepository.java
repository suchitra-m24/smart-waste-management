package com.smartwaste.smart_waste_backend.repository;
import com.smartwaste.smart_waste_backend.model.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RewardRepository
        extends JpaRepository<Reward, Integer> {

    // Get only active rewards
    List<Reward> findByActiveTrue();

    // Get rewards user can afford
    List<Reward> findByPointsRequiredLessThanEqual(int points);
}
