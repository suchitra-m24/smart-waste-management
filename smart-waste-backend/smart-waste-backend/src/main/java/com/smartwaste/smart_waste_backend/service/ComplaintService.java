package com.smartwaste.smart_waste_backend.service;

import com.smartwaste.smart_waste_backend.model.Complaint;
import com.smartwaste.smart_waste_backend.model.User;
import com.smartwaste.smart_waste_backend.repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private UserService userService;

    // REPORT COMPLAINT
    public Complaint reportComplaint(Complaint complaint, User citizen) {
        complaint.setCitizen(citizen);
        complaint.setStatus(Complaint.Status.REPORTED);
        // Give 10 eco points for reporting
        userService.addEcoPoints(citizen, 10);
        return complaintRepository.save(complaint);
    }

    // GET ALL COMPLAINTS
    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    // GET BY ID
    public Optional<Complaint> getComplaintById(int id) {
        return complaintRepository.findById(id);
    }

    // GET BY CITIZEN
    public List<Complaint> getComplaintsByCitizen(User citizen) {
        return complaintRepository.findByCitizen(citizen);
    }

    // GET BY COLLECTOR
    public List<Complaint> getComplaintsByCollector(User collector) {
        return complaintRepository.findByCollector(collector);
    }

    // GET BY STATUS
    public List<Complaint> getComplaintsByStatus(
            Complaint.Status status) {
        return complaintRepository.findByStatus(status);
    }

    // ASSIGN COLLECTOR
    public Complaint assignCollector(int complaintId, User collector) {
        Complaint complaint = complaintRepository
                .findById(complaintId)
                .orElseThrow(() ->
                        new RuntimeException("Complaint not found!"));
        complaint.setCollector(collector);
        complaint.setStatus(Complaint.Status.ASSIGNED);
        return complaintRepository.save(complaint);
    }

    // UPDATE STATUS
    public Complaint updateStatus(int complaintId,
                                  Complaint.Status status) {
        Complaint complaint = complaintRepository
                .findById(complaintId)
                .orElseThrow(() ->
                        new RuntimeException("Complaint not found!"));
        complaint.setStatus(status);

        // If collected give citizen 50 eco points
        if (status == Complaint.Status.COLLECTED) {
            complaint.setCollectedAt(LocalDateTime.now());
            userService.addEcoPoints(complaint.getCitizen(), 50);
        }
        return complaintRepository.save(complaint);
    }

    // GET STATS
    public Map<String, Long> getStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("total", complaintRepository.count());
        stats.put("reported",
                complaintRepository.countByStatus(
                        Complaint.Status.REPORTED));
        stats.put("assigned",
                complaintRepository.countByStatus(
                        Complaint.Status.ASSIGNED));
        stats.put("inProgress",
                complaintRepository.countByStatus(
                        Complaint.Status.IN_PROGRESS));
        stats.put("collected",
                complaintRepository.countByStatus(
                        Complaint.Status.COLLECTED));
        return stats;
    }

    // GET RECENT COMPLAINTS
    public List<Complaint> getRecentComplaints() {
        return complaintRepository
                .findTop10ByOrderByCreatedAtDesc();
    }
}