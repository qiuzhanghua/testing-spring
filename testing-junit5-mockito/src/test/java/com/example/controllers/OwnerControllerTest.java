package com.example.controllers;

import com.example.fauxspring.BindingResult;
import com.example.fauxspring.Model;
import com.example.model.Owner;
import com.example.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.quality.Strictness;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mock.Strictness.LENIENT;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @Mock(strictness = LENIENT)
    OwnerService ownerService;

    @Mock
    Model model;

    @InjectMocks
    OwnerController controller;

    @Mock
    BindingResult bindingResult;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @BeforeEach
    void setUp() {
        given(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture()))
                .willAnswer(invocation -> {
                     String name = invocation.getArgument(0);
                    if (name.equals("%Buck%")) {
                        return List.of(new Owner(1L, "Joe", "Buck"));
                    } else if (name.equals("%DontFindMe%")) {
                        return List.of();
                    } else if (name.equals("%FindMe%")) {
                        return List.of(new Owner(1L, "Joe", "Buck"),
                                new Owner(2L, "Joe", "Buck"));
                    }
                    throw new RuntimeException("Invalid Argument");
                });
    }

    @Test
    void processFindFormWildcardStringAnnotation() {
        // Given
        Owner owner = new Owner(1L, "Joe", "Buck");
         // When
        String viewName = controller.processFindForm(owner, bindingResult, null);
        // Then
        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("%Buck%");
        assertThat(viewName).isEqualToIgnoringCase("redirect:/owners/1");
        verifyNoInteractions(model);
    }

    @Test
    void processFindFormWildcardNotFound() {
        // Given
        Owner owner = new Owner(1L, "Joe", "DontFindMe");

        // When
        String viewName = controller.processFindForm(owner, bindingResult, null);

        // Then
        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("%DontFindMe%");
        assertThat(viewName).isEqualToIgnoringCase("owners/findOwners");
        verifyNoInteractions(model);
    }

    @Test
    void processFindFormWildcardFound() {
        // Given
        Owner owner = new Owner(1L, "Joe", "FindMe");
        InOrder inOrder = Mockito.inOrder(ownerService, model);

        // When
        String viewName = controller.processFindForm(owner, bindingResult, model);
        // Then
        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("%FindMe%");
        assertThat(viewName).isEqualToIgnoringCase("owners/ownersList");
        inOrder.verify(ownerService).findAllByLastNameLike(anyString());
        inOrder.verify(model, times(1)).addAttribute(anyString(), anyList());
        verifyNoMoreInteractions(model);
    }

    @Test
    void processCreationFormHasErrors() {
        // Given
        Owner owner = new Owner(1L, "Jim", "Bob");
        given(bindingResult.hasErrors()).willReturn(true);
        // When
        String viewName = controller.processCreationForm(owner, bindingResult);
        // Then
        assertThat(viewName).isEqualToIgnoringCase("owners/createOrUpdateOwnerForm");
    }

    @Test
    void processCreationFormNoErrors() {
        // Given
        Owner owner = new Owner(5L, "Jim", "Bob");
        given(bindingResult.hasErrors()).willReturn(false);
        given(ownerService.save(any())).willReturn(owner);
        // When
        String viewName = controller.processCreationForm(owner, bindingResult);
        // Then
        assertThat(viewName).isEqualToIgnoringCase("redirect:/owners/5");
    }
}
