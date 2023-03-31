package com.example.controllers;

import com.example.ControllerTests;
import com.example.fauxspring.Model;
import com.example.model.Vet;
import com.example.services.SpecialtyService;
import com.example.services.VetService;
import com.example.services.map.SpecialityMapService;
import com.example.services.map.VetMapService;
import com.example.fauxspring.ModelMapImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class VetControllerTest implements ControllerTests {

    VetService vetService;
    SpecialtyService specialtyService;

    VetController vetController;

    @BeforeEach
    void setUp() {
        specialtyService = new SpecialityMapService();
        vetService = new VetMapService(specialtyService);

        vetController = new VetController(vetService);

        Vet vet1 = new Vet(1L, "joe", "buck", null);
        Vet vet2 = new Vet(2L, "Jimmy", "Smith", null);

        vetService.save(vet1);
        vetService.save(vet2);
    }

    @Test
    void listVets() {
        Model model = new ModelMapImpl();

        String view = vetController.listVets(model);

        assertThat("vets/index").isEqualTo(view);

        Set modelAttribute = (Set) ((ModelMapImpl) model).getMap().get("vets");

        assertThat(modelAttribute.size()).isEqualTo(2);
    }
}