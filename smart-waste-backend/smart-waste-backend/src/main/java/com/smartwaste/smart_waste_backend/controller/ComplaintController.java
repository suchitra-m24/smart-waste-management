package com.smartwaste.smart_waste_backend.controller;

import com.smartwaste.smart_waste_backend.model.Complaint;
import com.smartwaste.smart_waste_backend.model.User;
import com.smartwaste.smart_waste_backend.service.ComplaintService;
import com.smartwaste.smart_waste_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/complaints")
@CrossOrigin(origins = "http://localhost:3000")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private UserService userService;

    // REPORT COMPLAINT (CITIZEN)
    @PostMapping("/report")
    public ResponseEntity<Complaint> reportComplaint(
            @RequestBody Complaint complaint,
            Authentication authentication) {

        User citizen = (User) authentication.getPrincipal();
        return ResponseEntity.ok(
                complaintService.reportComplaint(complaint, citizen));
    }

    // GET ALL COMPLAINTS (PUBLIC)
    @GetMapping("/all")
    public ResponseEntity<List<Complaint>> getAllComplaints() {
        return ResponseEntity.ok(
                complaintService.getAllComplaints());
    }

    // GET MY COMPLAINTS (CITIZEN)
    @GetMapping("/my-complaints")
    public ResponseEntity<List<Complaint>> getMyComplaints(
            Authentication authentication) {
        User citizen = (User) authentication.getPrincipal();
        return ResponseEntity.ok(
                complaintService.getComplaintsByCitizen(citizen));
    }

    // GET ASSIGNED COMPLAINTS (COLLECTOR)
    @GetMapping("/assigned")
    public ResponseEntity<List<Complaint>> getAssignedComplaints(
            Authentication authentication) {
        User collector = (User) authentication.getPrincipal();
        return ResponseEntity.ok(
                complaintService.getComplaintsByCollector(collector));
    }

    // GET BY STATUS (ADMIN)
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Complaint>> getByStatus(
            @PathVariable Complaint.Status status) {
        return ResponseEntity.ok(
                complaintService.getComplaintsByStatus(status));
    }

    // ASSIGN COLLECTOR (ADMIN)
    @PutMapping("/{complaintId}/assign/{collectorId}")
    public ResponseEntity<Complaint> assignCollector(
            @PathVariable int complaintId,
            @PathVariable int collectorId) {

        User collector = userService.getUserById(collectorId)
                .orElseThrow(() ->
                        new RuntimeException("Collector not found!"));
        return ResponseEntity.ok(
                complaintService.assignCollector(
                        complaintId, collector));
    }

    // UPDATE STATUS
    @PutMapping("/{complaintId}/status")
    public ResponseEntity<Complaint> updateStatus(
            @PathVariable int complaintId,
            @RequestParam Complaint.Status status) {

        return ResponseEntity.ok(
                complaintService.updateStatus(complaintId, status));
    }

    // GET STATS (PUBLIC)
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> getStats() {
        return ResponseEntity.ok(complaintService.getStats());
    }

    // GET RECENT (PUBLIC)
    @GetMapping("/recent")
    public ResponseEntity<List<Complaint>> getRecent() {
        return ResponseEntity.ok(
                complaintService.getRecentComplaints());
    }
}