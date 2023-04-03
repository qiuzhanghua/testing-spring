package com.example.web.controllers;

import com.example.web.model.BeerPagedList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BeerControllerIT {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void testListBeers() {
        BeerPagedList beers = restTemplate.getForObject("/api/v1/beer", BeerPagedList.class);
        Assertions.assertThat(beers.getContent()).hasSize(3);
    }
}
