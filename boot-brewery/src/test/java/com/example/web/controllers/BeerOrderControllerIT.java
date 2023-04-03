package com.example.web.controllers;

import com.example.domain.Customer;
import com.example.repositories.BeerOrderRepository;
import com.example.repositories.CustomerRepository;
import com.example.web.model.BeerOrderPagedList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BeerOrderControllerIT {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private CustomerRepository customerRepository;
    Customer customer;

    @BeforeEach
    void setUp() {
        customer = customerRepository.findAll().get(0);
    }
    
    @Test
    void testListOrders() {
        BeerOrderPagedList beerOrderPagedList = restTemplate.getForObject("/api/v1/customers/" + customer.getId() + "/orders", BeerOrderPagedList.class);
        Assertions.assertThat(beerOrderPagedList.getContent()).hasSize(1);
    }

}
