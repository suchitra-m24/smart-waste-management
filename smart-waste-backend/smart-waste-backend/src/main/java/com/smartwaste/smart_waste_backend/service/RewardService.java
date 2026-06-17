package com.smartwaste.smart_waste_backend.service;

import com.smartwaste.smart_waste_backend.model.Redemption;
import com.smartwaste.smart_waste_backend.model.Reward;
import com.smartwaste.smart_waste_backend.model.User;
import com.smartwaste.smart_waste_backend.repository.RedemptionRepository;
import com.smartwaste.smart_waste_backend.repository.RewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RewardService {

    @Autowired
    private RewardRepository rewardRepository;

    @Autowired
    private RedemptionRepository redemptionRepository;

    @Autowired
    private UserService userService;

    // GET ALL ACTIVE REWARDS
    public List<Reward> getAllActiveRewards() {
        return rewardRepository.findByActiveTrue();
    }

    // GET AFFORDABLE REWARDS
    public List<Reward> getAffordableRewards(int points) {
        return rewardRepository
                .findByPointsRequiredLessThanEqual(points);
    }

    // ADD REWARD (ADMIN)
    public Reward addReward(Reward reward) {
        return rewardRepository.save(reward);
    }

    // REDEEM REWARD
    public String redeemReward(int rewardId, User user) {
        Reward reward = rewardRepository.findById(rewardId)
                .orElseThrow(() ->
                        new RuntimeException("Reward not found!"));

        // Check if reward is active
        if (!reward.isActive()) {
            return "Reward is not available!";
        }

        // Check if enough quantity
        if (reward.getQuantity() <= 0) {
            return "Reward is out of stock!";
        }

        // Check if user has enough points
        if (user.getEcoPoints() < reward.getPointsRequired()) {
            return "Not enough eco points! You need "
                    + reward.getPointsRequired()
                    + " points but have "
                    + user.getEcoPoints();
        }

        // Deduct points
        userService.deductEcoPoints(user,
                reward.getPointsRequired());

        // Reduce quantity
        reward.setQuantity(reward.getQuantity() - 1);
        rewardRepository.save(reward);

        // Save redemption
        Redemption redemption = new Redemption();
        redemption.setUser(user);
        redemption.setReward(reward);
        redemption.setPointsSpent(reward.getPointsRequired());
        redemptionRepository.save(redemption);

        return "Reward redeemed successfully!";
    }

    // GET USER REDEMPTIONS
    public List<Redemption> getUserRedemptions(User user) {
        return redemptionRepository.findByUser(user);
    }

    // DELETE REWARD (ADMIN)
    public String deleteReward(int id) {
        rewardRepository.deleteById(id);
        return "Reward deleted!";
    }
}