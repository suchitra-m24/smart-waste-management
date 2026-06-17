package com.smartwaste.smart_waste_backend.service;

import com.smartwaste.smart_waste_backend.model.User;
import com.smartwaste.smart_waste_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // REGISTER
    public String registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return "Email already exists!";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully!";
    }

    // GET BY EMAIL
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // GET BY ID
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    // GET ALL COLLECTORS
    public List<User> getAllCollectors() {
        return userRepository.findByRole(User.Role.COLLECTOR);
    }

    // GET ALL CITIZENS
    public List<User> getAllCitizens() {
        return userRepository.findByRole(User.Role.CITIZEN);
    }

    // SAVE USER
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // ADD ECO POINTS
    public void addEcoPoints(User user, int points) {
        user.setEcoPoints(user.getEcoPoints() + points);
        userRepository.save(user);
    }

    // DEDUCT ECO POINTS
    public boolean deductEcoPoints(User user, int points) {
        if (user.getEcoPoints() < points) {
            return false;
        }
        user.setEcoPoints(user.getEcoPoints() - points);
        userRepository.save(user);
        return true;
    }

    // GET LEADERBOARD
    public List<User> getLeaderboard() {
        return userRepository.findByRole(User.Role.CITIZEN)
                .stream()
                .sorted((a, b) -> b.getEcoPoints() - a.getEcoPoints())
                .limit(10)
                .toList();
    }
}