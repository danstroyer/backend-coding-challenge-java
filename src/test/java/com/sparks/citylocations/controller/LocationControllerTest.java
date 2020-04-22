package com.sparks.citylocations.controller;

import com.sparks.citylocations.model.Location;
import com.sparks.citylocations.service.LocationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(LocationController.class)
public class LocationControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    LocationService locationService;


    //TODO
    //Faced issue with autowired  component missing to execute the test, need to research on it
    @Autowired
    LocationModelAssembler assembler;

    @Before
    public void createTestingObjects() {
        Location location1 = new Location();
        location1.setId(4627983L);
        location1.setName("London");

        List<Location> result = new ArrayList<Location>();
        result.add(location1);

        LocationModelAssembler assembler = new LocationModelAssembler();

        when(locationService.getSuggestedLocations("Lond")).thenReturn(result.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList()));
    }

    @Test
    public void shouldReturnSuggestion() throws Exception{

        mockMvc.perform(get("/suggestions?q=Lond").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", is(notNullValue())))
                .andExpect(jsonPath("$.content[0].id",is(4627983)));

    }

}
