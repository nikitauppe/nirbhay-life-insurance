package com.nikita.insurance.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nikita.insurance.entity.Policy;
import com.nikita.insurance.exception.ResourceNotFoundException;
import com.nikita.insurance.repository.PolicyRepository;

@ExtendWith(MockitoExtension.class)
class PolicyServiceTest {

    @Mock
    private PolicyRepository repo;

    @InjectMocks
    private PolicyService service;

    @Test
    void getById_returnsPolicy_whenFound() {
        Policy p = new Policy();
        p.setId(1L);
        p.setHolderName("Ravi");
        when(repo.findById(1L)).thenReturn(Optional.of(p));

        Policy result = service.getById(1L);

        assertEquals("Ravi", result.getHolderName());
    }

    @Test
    void getById_throwsException_whenNotFound() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getById(99L));
    }
}