package com.nikita.insurance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nikita.insurance.entity.Policy;
import com.nikita.insurance.entity.User;

public interface PolicyRepository extends JpaRepository<Policy, Long> {
    List<Policy> findByPolicyType(String policyType);
    List<Policy> findByUser(User user);
    List<Policy> findByUserUsername(String username);
}