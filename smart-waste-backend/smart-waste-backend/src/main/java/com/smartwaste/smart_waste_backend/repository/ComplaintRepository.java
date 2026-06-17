package com.smartwaste.smart_waste_backend.repository;
import com.smartwaste.smart_waste_backend.model.Complaint;
import com.smartwaste.smart_waste_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public  interface ComplaintRepository extends JpaRepository<Complaint, Integer>  {
    // Get all complaints by citizen
    List<Complaint> findByCitizen(User citizen);

    // Get all complaints by collector
    List<Complaint> findByCollector(User collector);

    // Get complaints by status
    List<Complaint> findByStatus(Complaint.Status status);

    // Get complaints by citizen and status
    List<Complaint> findByCitizenAndStatus(
            User citizen, Complaint.Status status);

    // Count complaints by status
    long countByStatus(Complaint.Status status);

    // Count complaints by citizen
    long countByCitizen(User citizen);

    // Get recent complaints
    List<Complaint> findTop10ByOrderByCreatedAtDesc();

    // Get stats query
    @Query("SELECT COUNT(c) FROM Complaint c " +
            "WHERE c.status = 'COLLECTED'")
    long countCollected();

    @Query("SELECT COUNT(c) FROM Complaint c " +
            "WHERE c.status = 'REPORTED'")
    long countPending();
}
