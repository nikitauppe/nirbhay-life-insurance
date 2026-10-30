package com.nikita.insurance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nikita.insurance.entity.Policy;
import com.nikita.insurance.entity.User;
import com.nikita.insurance.exception.ResourceNotFoundException;
import com.nikita.insurance.repository.PolicyRepository;
import com.nikita.insurance.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PolicyRepository policyRepo;

    public record UserResponse(Long id, String username, String email, String role) {
    }

    @GetMapping
    public List<UserResponse> all() {
        return userRepo.findAll().stream()
                .map(u -> new UserResponse(u.getId(), u.getUsername(), u.getEmail(), u.getRole()))
                .toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        User u = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
        if ("ADMIN".equals(u.getRole())) {
            return ResponseEntity.badRequest().body("Admin cannot be deleted");
        }
        // keep the policies, just unassign them
        for (Policy p : policyRepo.findByUser(u)) {
            p.setUser(null);
            policyRepo.save(p);
        }
        userRepo.delete(u);
        return ResponseEntity.ok("User deleted");
    }
}