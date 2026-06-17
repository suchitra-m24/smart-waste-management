package com.smartwaste.smart_waste_backend.controller;

import com.smartwaste.smart_waste_backend.model.Complaint;
import com.smartwaste.smart_waste_backend.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/photos")
@CrossOrigin(origins = "http://localhost:3000")
public class PhotoController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private ComplaintService complaintService;

    // UPLOAD PHOTO
    @PostMapping("/upload/{complaintId}")
    public ResponseEntity<String> uploadPhoto(
            @RequestParam("file") MultipartFile file,
            @PathVariable int complaintId) {

        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = UUID.randomUUID()
                    + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath);

            // Update complaint with photo url
            Complaint complaint = complaintService
                    .getComplaintById(complaintId)
                    .orElseThrow(() ->
                            new RuntimeException("Complaint not found"));
            complaint.setPhotoUrl(fileName);
            complaintService.reportComplaint(
                    complaint, complaint.getCitizen());

            return ResponseEntity.ok(fileName);
        } catch (IOException e) {
            return ResponseEntity.status(500)
                    .body("Error uploading photo!");
        }
    }

    // VIEW PHOTO
    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewPhoto(
            @PathVariable String fileName) {
        try {
            Path filePath = Paths.get(uploadDir)
                    .resolve(fileName);
            Resource resource = new UrlResource(
                    filePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE,
                                "image/jpeg")
                        .body(resource);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}