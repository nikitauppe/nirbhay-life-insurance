package com.nikita.insurance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nikita.insurance.entity.Policy;
import com.nikita.insurance.service.PolicyService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/policies")
public class PolicyController {

    @Autowired
    private PolicyService service;

    // Any logged-in user: only their own policies
    @GetMapping("/my")
    public List<Policy> mine(Authentication auth) {
        return service.getByUsername(auth.getName());
    }

    // Everything below is ADMIN only (see SecurityConfig)
    @PostMapping
    public Policy add(@Valid @RequestBody Policy p) {
        return service.save(p);
    }

    @GetMapping
    public List<Policy> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Policy getOne(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Policy update(@PathVariable Long id, @Valid @RequestBody Policy p) {
        return service.update(id, p);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Policy deleted";
    }

    @GetMapping("/type/{type}")
    public List<Policy> byType(@PathVariable String type) {
        return service.getByType(type);
    }

    @GetMapping("/sort/{field}")
    public List<Policy> sorted(@PathVariable String field) {
        return service.getSorted(field);
    }
}