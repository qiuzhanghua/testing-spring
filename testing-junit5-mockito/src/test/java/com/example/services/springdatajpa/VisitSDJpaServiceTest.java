package com.example.services.springdatajpa;

import com.example.model.Visit;
import com.example.repositories.VisitRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {
    @Mock
    VisitRepository visitRepository;

    @InjectMocks
    VisitSDJpaService service;

    @Test
    void findAll() {
        Set<Visit> visitSet = new HashSet<>();
        Visit visit = new Visit(1L, LocalDate.now());
        visitSet.add(visit);

        when(visitRepository.findAll()).thenReturn(visitSet);

        Set<Visit> visits = service.findAll();
        verify(visitRepository).findAll();
//        assertThat(visits).hasSize(1).isEqualTo(visitSet);
    }

    @Test
    void findById() {
        Visit visit = new Visit(1L, LocalDate.now());
        when(visitRepository.findById(1L)).thenReturn(java.util.Optional.of(visit));
        Visit found = service.findById(1L);
        verify(visitRepository).findById(anyLong());
//        assertThat(found).isNotNull().isEqualTo(visit);
    }

    @Test
    void save() {
        Visit visit = new Visit(1L, LocalDate.now());
        when(visitRepository.save(visit)).thenReturn(visit);
        Visit saved = service.save(visit);
        verify(visitRepository).save(visit);
    }

    @Test
    void delete() {
        Visit visit = new Visit(1L, LocalDate.now());
        service.delete(visit);
        verify(visitRepository).delete(visit);
    }

    @Test
    void deleteById() {
        long id = 2L;
        service.deleteById(id);
        verify(visitRepository).deleteById(id);
    }
}