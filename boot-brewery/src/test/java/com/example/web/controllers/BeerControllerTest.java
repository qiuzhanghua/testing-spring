package com.example.web.controllers;

import com.example.services.BeerService;
import com.example.web.model.BeerDto;
import com.example.web.model.BeerPagedList;
import com.example.web.model.BeerStyleEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @MockBean
    BeerService beerService;

    @Autowired
    MockMvc mockMvc;
    BeerDto validBeer;



    @BeforeEach
    void setUp() {
        validBeer = BeerDto.builder().id(UUID.randomUUID())
                .version(1)
                .beerName("Beer1")
                .beerStyle(BeerStyleEnum.PALE_ALE)
                .price(new BigDecimal("12.99"))
                .quantityOnHand(4)
                .upc(123456789012L)
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .build();
    }

    @AfterEach
    void tearDown() {
        reset(beerService);
    }

    @Test
    void testGetBeerById() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");

        given(beerService.findBeerById(any())).willReturn(validBeer);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beer/" + validBeer.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(validBeer.getId().toString()))
                .andExpect(jsonPath("$.beerName").value("Beer1"))
                .andExpect(jsonPath("$.createdDate").value(formatter.format(validBeer.getCreatedDate())))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Nested
    class TestListOperations {
        @Captor
        ArgumentCaptor<String> beerNameCaptor;
        @Captor
        ArgumentCaptor<BeerStyleEnum> beerStyleEnumCaptor;
        @Captor
        ArgumentCaptor<PageRequest> pageRequestCaptor;
        BeerPagedList beerPagedList;

        @BeforeEach
        void setUp() {
            List<BeerDto> beerDtoList = new ArrayList<>();
            beerDtoList.add(validBeer);
            BeerDto anothor = BeerDto.builder().id(UUID.randomUUID())
                    .version(1)
                    .beerName("Beer1")
                    .beerStyle(BeerStyleEnum.PALE_ALE)
                    .price(new BigDecimal("12.99"))
                    .quantityOnHand(4)
                    .upc(123456789012L)
                    .createdDate(OffsetDateTime.now())
                    .lastModifiedDate(OffsetDateTime.now())
                    .build();
            beerDtoList.add(anothor);
            beerPagedList = new BeerPagedList(beerDtoList, PageRequest.of(1, 1), 2L);
            given(beerService.listBeers(beerNameCaptor.capture(), beerStyleEnumCaptor.capture(), pageRequestCaptor.capture()))
                    .willReturn(beerPagedList);

        }

        @Test
        void testListBeers() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beer").accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.content", hasSize(2)))
                    .andExpect(jsonPath("$.content[0].id").value(validBeer.getId().toString()));
        }
    }

//    public MappingJackson2HttpMessageConverter jacksonMessageConverter() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.configure(WRITE_DATES_AS_TIMESTAMPS, false);
//        objectMapper.configure(WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
//        objectMapper.registerModule(new JavaTimeModule());
//        objectMapper.setSerializationInclusion(NON_NULL);
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(objectMapper);
//        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
//        return converter;
//    }

}