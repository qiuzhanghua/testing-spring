package com.example.services.springdatajpa;

import com.example.model.Speciality;
import com.example.repositories.SpecialityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock
    SpecialityRepository specialityRepository;

    @InjectMocks
    SpecialitySDJpaService service;

    @Test
    void deleteByObject() {
        service.delete(new Speciality());
        verify(specialityRepository).delete(any(Speciality.class));
    }

    @Test
    void findByIdTest() {
        Speciality speciality = new Speciality();
        when(specialityRepository.findById(1L)).thenReturn(java.util.Optional.of(speciality));
        Speciality foundSpeciality = service.findById(1L);
        assertThat(foundSpeciality).isNotNull();
        verify(specialityRepository).findById(1L);
    }

    @Test
    void deleteById() {
        service.deleteById(1L);
        service.deleteById(1L);
        verify(specialityRepository, times(2)).deleteById(1L);
    }

    @Test
    void deleteByIdAtLeast() {
        service.deleteById(1L);
        service.deleteById(1L);
        verify(specialityRepository, atLeast(1)).deleteById(1L);
        verify(specialityRepository, never()).deleteById(3L);
    }

    @Test
    void delete() {
        service.delete(new Speciality());
    }
}