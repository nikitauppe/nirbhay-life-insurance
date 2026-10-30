package com.nikita.insurance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nikita.insurance.entity.Policy;
import com.nikita.insurance.entity.User;
import com.nikita.insurance.exception.ResourceNotFoundException;
import com.nikita.insurance.repository.PolicyRepository;
import com.nikita.insurance.repository.UserRepository;

@Service
public class PolicyService {

    @Autowired
    private PolicyRepository repo;

    @Autowired
    private UserRepository userRepo;

    private void assignUser(Policy p, Long userId) {
        if (userId == null) {
            p.setUser(null);
            return;
        }
        User u = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
        p.setUser(u);
    }

    public Policy save(Policy p) {
        assignUser(p, p.getUserId());
        return repo.save(p);
    }

    public List<Policy> getAll() {
        return repo.findAll();
    }

    public Policy getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found: " + id));
    }

    public Policy update(Long id, Policy newData) {
        Policy p = getById(id);
        p.setPolicyType(newData.getPolicyType());
        p.setHolderName(newData.getHolderName());
        p.setPremium(newData.getPremium());
        p.setSumAssured(newData.getSumAssured());
        p.setStartDate(newData.getStartDate());
        p.setEndDate(newData.getEndDate());
        assignUser(p, newData.getUserId());
        return repo.save(p);
    }

    public void delete(Long id) {
        repo.delete(getById(id));
    }

    public List<Policy> getByType(String type) {
        return repo.findByPolicyType(type);
    }

    public List<Policy> getSorted(String field) {
        List<String> allowed = List.of("id", "policyType", "holderName",
                "premium", "sumAssured", "startDate", "endDate");
        if (!allowed.contains(field)) {
            field = "id";
        }
        return repo.findAll(Sort.by(field));
    }

    public List<Policy> getByUsername(String username) {
        return repo.findByUserUsername(username);
    }
}