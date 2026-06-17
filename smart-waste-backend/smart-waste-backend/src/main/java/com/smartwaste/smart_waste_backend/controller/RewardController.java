package com.smartwaste.smart_waste_backend.controller;

import com.smartwaste.smart_waste_backend.model.Redemption;
import com.smartwaste.smart_waste_backend.model.Reward;
import com.smartwaste.smart_waste_backend.model.User;
import com.smartwaste.smart_waste_backend.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/rewards")
@CrossOrigin(origins = "http://localhost:3000")
public class RewardController {

    @Autowired
    private RewardService rewardService;

    // GET ALL ACTIVE REWARDS
    @GetMapping("/all")
    public ResponseEntity<List<Reward>> getAllRewards() {
        return ResponseEntity.ok(
                rewardService.getAllActiveRewards());
    }

    // GET AFFORDABLE REWARDS
    @GetMapping("/affordable")
    public ResponseEntity<List<Reward>> getAffordableRewards(
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(
                rewardService.getAffordableRewards(
                        user.getEcoPoints()));
    }

    // ADD REWARD (ADMIN)
    @PostMapping("/add")
    public ResponseEntity<Reward> addReward(
            @RequestBody Reward reward) {
        return ResponseEntity.ok(rewardService.addReward(reward));
    }

    // REDEEM REWARD (CITIZEN)
    @PostMapping("/redeem/{rewardId}")
    public ResponseEntity<String> redeemReward(
            @PathVariable int rewardId,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(
                rewardService.redeemReward(rewardId, user));
    }

    // GET MY REDEMPTIONS
    @GetMapping("/my-redemptions")
    public ResponseEntity<List<Redemption>> getMyRedemptions(
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(
                rewardService.getUserRedemptions(user));
    }

    // DELETE REWARD (ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReward(
            @PathVariable int id) {
        return ResponseEntity.ok(rewardService.deleteReward(id));
    }
}