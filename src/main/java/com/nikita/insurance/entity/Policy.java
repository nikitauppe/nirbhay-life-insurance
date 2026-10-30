package com.nikita.insurance.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Entity
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String policyType;

    @NotBlank
    private String holderName;

    @Positive
    private double premium;

    @Positive
    private double sumAssured;

    private LocalDate startDate;

    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Transient
    private Long userId; // used only for input from the admin

    public Policy() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPolicyType() { return policyType; }
    public void setPolicyType(String policyType) { this.policyType = policyType; }

    public String getHolderName() { return holderName; }
    public void setHolderName(String holderName) { this.holderName = holderName; }

    public double getPremium() { return premium; }
    public void setPremium(double premium) { this.premium = premium; }

    public double getSumAssured() { return sumAssured; }
    public void setSumAssured(double sumAssured) { this.sumAssured = sumAssured; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Long getUserId() { return user != null ? user.getId() : userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getAssignedTo() { return user != null ? user.getUsername() : null; }
}