package org.springframework.samples.petclinic.web;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
class VetControllerTest {

    @Mock
    ClinicService clinicService;

    @Mock
    Map<String,Object> model;

    @InjectMocks
    VetController controller;

    List<Vet> vets = new ArrayList<>();

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        Vet vet1 = new Vet();
        vet1.setId(1);
        vet1.setFirstName("John");
        vet1.setLastName("Doe");
        vets.add(vet1);

        Vet vet2 = new Vet();
        vet2.setId(2);
        vet2.setFirstName("Jane");
        vet2.setLastName("Doe");
        vets.add(vet2);
        given(clinicService.findVets()).willReturn(vets);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testShowResourcesVetList() throws Exception {
        mockMvc.perform(get("/vets"))
                .andExpect(status().isOk())
                .andExpect(view().name("vets/vetList"))
                .andExpect(model().attributeExists("vets"));
    }

    @Test
    void showVetList() {
        String view = controller.showVetList(model);
        then(clinicService).should().findVets();
        then(model).should().put(anyString(), any());
        Assertions.assertThat(view).isEqualToIgnoringCase("vets/vetList");
    }
}