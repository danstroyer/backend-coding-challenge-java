package com.sparks.citylocations.service;

import com.sparks.citylocations.controller.LocationModelAssembler;
import com.sparks.citylocations.exception.LocationNotFoundException;
import com.sparks.citylocations.model.Location;
import com.sparks.citylocations.repository.LocationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer implementation for cities API
 */
@Slf4j
@Service
public class LocationService {

    private final LocationRepository repository;
    private final LocationModelAssembler assembler;

    public LocationService(LocationRepository repository, LocationModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Method that retrieves all the locations available on the system
     * @return
     */
    @Cacheable("locations")
    public List<EntityModel<Location>> getLocations(){
        log.info("--- LocationService::getLocations() -- ");
        return repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
    }

    /**
     * Method to retrieve a specific location provided an id
     * @param id
     * @return
     */
    @Cacheable("singleLocation")
    public Location getLocation(Long id){
        log.info("--- LocationService::getLocation() -- ");
        return repository.findById(id)
                .orElseThrow(() -> new LocationNotFoundException(id));
    }

    /**
     * Method to retrieve a locations array with the cities matching the query name
     * @param query
     * @return
     */
    @Cacheable("suggestion")
    public List<EntityModel<Location>> getSuggestedLocations(String query){
        log.info("--- LocationService::getSuggestedLocations() --  received query: " + query);

        // - get the possible matches from the cities
        List<Location> suggestions = repository.findAll().stream()
                .filter(location -> location.getName().contains(query))
                .collect(Collectors.toList());

        // - score the results and sort them by the score
        qualifyResults(suggestions, query);
        suggestions.sort(new Comparator<Location>() {
            @Override
            public int compare(Location location1, Location location2) {
                return location2.getScore().compareTo(location1.getScore());
            }
        });

        // - return the final entity collection
        return suggestions.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

    }

    /**
     * Method to qualify the results and assing the proper score
     * @param suggestions
     * @param query
     */
    private void qualifyResults(List<Location> suggestions, String query){

        // - set  the results with proper scoring
        suggestions.stream()
            .forEach(location -> {
                location.setScore(getScoreForName(location.getName(), query));
            });

    }


    /**
     * Method to calculate the score only for the city name
     * @param fullName
     * @param query
     * @return
     */
    private float getScoreForName(String fullName, String query){

        String name = Arrays.stream(fullName.split(",")).findFirst().get().toString();
        float queryLenght = query.length();
        float nameLenght = name.length();

        BigDecimal roundedValue = new BigDecimal(queryLenght / nameLenght);
        return roundedValue.setScale(2, RoundingMode.HALF_UP).floatValue();
    }
}
