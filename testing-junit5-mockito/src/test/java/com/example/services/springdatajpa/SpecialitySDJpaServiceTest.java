package com.example.services.springdatajpa;

import com.example.model.Speciality;
import com.example.repositories.SpecialityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock(strictness = Mock.Strictness.LENIENT)
    SpecialityRepository specialityRepository;

    @InjectMocks
    SpecialitySDJpaService service;

    @Test
    void deleteByObject() {
        // Given
        // When
        service.delete(new Speciality());
        // Then
        then(specialityRepository).should().delete(any(Speciality.class));
//        verify(specialityRepository).delete(any(Speciality.class));
    }

    @Test
    void findByIdTest() {
        Speciality speciality = new Speciality();
        when(specialityRepository.findById(1L)).thenReturn(java.util.Optional.of(speciality));
        Speciality foundSpeciality = service.findById(1L);
        assertThat(foundSpeciality).isNotNull();
        then(specialityRepository).should(timeout(100)).findById(1L);
        then(specialityRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void findByIdBDDTest() {
        Speciality speciality = new Speciality();
        // Given
        given(specialityRepository.findById(1L)).willReturn(java.util.Optional.of(speciality));
        // When
        Speciality foundSpeciality = service.findById(1L);
        // Then
        assertThat(foundSpeciality).isNotNull();
//        then(specialityRepository).should().findById(1L);
        then(specialityRepository).should(times(1)).findById(1L);
        then(specialityRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void deleteById() {
        // Given -- none
        // When
        service.deleteById(1L);
        service.deleteById(1L);
        // Then
        then(specialityRepository).should(timeout(100).times(2)).deleteById(1L);
        then(specialityRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void deleteByIdAtLeast() {
        // Given -- none
        // When
        service.deleteById(1L);
        service.deleteById(1L);
        // Then
        then(specialityRepository).should(timeout(1000).atLeastOnce()).deleteById(1L);
        then(specialityRepository).shouldHaveNoMoreInteractions();
        then(specialityRepository).should(never()).deleteById(3L);
//        verify(specialityRepository, atLeast(1)).deleteById(1L);
//        verify(specialityRepository, never()).deleteById(3L);
    }

    @Test
    void delete() {
        service.delete(new Speciality());
        then(specialityRepository).should().delete(any(Speciality.class));
    }

    @Test
    void testDoThrow() {
        doThrow(new RuntimeException("boom")).when(specialityRepository).delete(any());
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> specialityRepository.delete(new Speciality()));
        verify(specialityRepository).delete(any());
    }

    @Test
    void testFindByIdThrows() {
        given(specialityRepository.findById(1L)).willThrow(new RuntimeException("boom"));
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> specialityRepository.findById(1L));
        then(specialityRepository).should().findById(1L);
    }

    @Test
    void testDeleteBDD() {
        willThrow(new RuntimeException("boom")).given(specialityRepository).delete(any());
        org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> service.delete(new Speciality()));
        then(specialityRepository).should().delete(any(Speciality.class));
    }

    @Test
    void testSaveLambda() {
        String matchMe = "matchMe";
        Speciality speciality = new Speciality();
        speciality.setDescription(matchMe);
        given(specialityRepository.save(argThat(argument -> argument.getDescription().equals(matchMe)))).willReturn(speciality);
        Speciality savedSpeciality = service.save(speciality);
        assertThat(savedSpeciality.getDescription()).isEqualTo(matchMe);
    }

    @Test
    void testSaveLambdaNotMatch() {
        String matchMe = "matchMe";
        Speciality speciality = new Speciality();
        speciality.setDescription("Net Match");
        given(specialityRepository.save(argThat(argument -> argument.getDescription().equals(matchMe)))).willReturn(speciality);
        Speciality savedSpeciality = service.save(speciality);
        assertThat(savedSpeciality).isNull();
    }
}