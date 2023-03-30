package com.example.controllers;

import com.example.fauxspring.Model;
import com.example.fauxspring.ModelMapImpl;
import com.example.model.Vet;
import com.example.services.SpecialityService;
import com.example.services.VetService;
import com.example.services.map.SpecialityMapService;
import com.example.services.map.VetMapService;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class VetControllerTest {
    VetController vetController;
    VetService vetService;
    SpecialityService specialityService;

    @BeforeEach
    void setUp() {
        specialityService = new SpecialityMapService();
        vetService = new VetMapService(specialityService);
        vetController = new VetController(vetService);
        Vet vet1 = new Vet(1L, "Joe", "Buck", null);
        Vet vet2 = new Vet(2L, "Jimmy", "Smith", null);
        vetService.save(vet1);
        vetService.save(vet2);
    }

    @Test
    void listVets() {
        ModelMapImpl model = new ModelMapImpl();
        String view = vetController.listVets(model);
        assertThat("vets/index").isEqualTo(view);
        Set attribute = (Set) model.getMap().get("vets");
        assertThat(attribute.size()).isEqualTo(2);
    }
}