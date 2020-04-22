package com.sparks.citylocations.service;


import com.sparks.citylocations.exception.LocationNotFoundException;
import com.sparks.citylocations.model.Location;
import com.sparks.citylocations.repository.LocationRepository;
import lombok.extern.slf4j.Slf4j;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;


import static org.junit.Assert.*;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class LocationServiceTest {
    @Autowired
    private LocationService locationService;

    @MockBean
    LocationRepository locationRepository;

    @Before
    public void setupFindById(){
        Location location = new Location();
        location.setId(4627983L);
        location.setName("Tested City");

        Mockito.when(locationRepository.findById(4627983L)).thenReturn(Optional.of(location));
    }

    @Test
    public void shouldFindLocationById(){
        log.info("Test that a city is found and is named 'Tested City'");
        Location savedLocation = locationService.getLocation(4627983L);
        assertNotNull("Location must be not null", savedLocation);
        assertThat(savedLocation.getName(),equalTo("Tested City"));
    }

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void shouldThrowNotFoundException() throws Exception{
        log.info("Test that exception is thrown when city by id is not found");
        exceptionRule.expect(LocationNotFoundException.class);
        exceptionRule.expectMessage("Could not find city id: 2322224");
        locationService.getLocation(2322224L);
    }

    @Before
    public void setupAllLocations(){
        Location location1 = new Location();
        location1.setId(4627983L);
        location1.setName("Tested City");

        Location location2 = new Location();
        location2.setId(2333L);
        location2.setName("Tested City");

        List<Location> result = new ArrayList<Location>();
        result.add(location1);
        result.add(location2);

        Mockito.when(locationRepository.findAll()).thenReturn(result);
    }

    @Test
    public void shouldReturnAllLocations(){
        log.info("Test that retrieved locations List contains 2 items");
        assertTrue(locationService.getLocations().size() == 2);
    }


    @Before
    public void createTestingObjects() {
        Location location1 = new Location();
        location1.setId(4627983L);
        location1.setName("Los Angeles");

        Location location2 = new Location();
        location2.setId(2333L);
        location2.setName("San Angel");

        Location location3 = new Location();
        location3.setId(2333L);
        location3.setName("Montreal");

        Location location4 = new Location();
        location4.setId(2333L);
        location4.setName("Quebec");

        List<Location> result = new ArrayList<Location>();
        result.add(location1);
        result.add(location2);
        result.add(location3);
        result.add(location4);

        Mockito.when(locationRepository.findAll()).thenReturn(result);
    }

    @Test
    public void shouldGetQueryResult(){
        //TODO
        // Complications with the test for stream mocking, dig further on the topic
        assertTrue(locationService.getSuggestedLocations("Angel").size() == 2);
    }
}

