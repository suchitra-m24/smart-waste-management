package com.smartwaste.smart_waste_backend.repository;
import com.smartwaste.smart_waste_backend.model.Redemption;
import com.smartwaste.smart_waste_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RedemptionRepository
        extends JpaRepository<Redemption, Integer> {

    // Get all redemptions by user
    List<Redemption> findByUser(User user);

    // Count redemptions by user
    long countByUser(User user);
}
