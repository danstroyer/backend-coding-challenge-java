package com.sparks.citylocations.controller;

import com.sparks.citylocations.model.Location;
import com.sparks.citylocations.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Controller Layer logic for the cities API
 */
@Slf4j
@RestController
public class LocationController {

    private final LocationService service;
    private final LocationModelAssembler assembler;


    /**
     * Controller constructor method
     * @param service
     * @param assembler
     */
    LocationController(LocationService service, LocationModelAssembler assembler){
        this.service = service;
        this.assembler = assembler;
    }


    /**
     * Mapping to retrieve all the locations available
     * @return
     */
    @GetMapping("/cities")
    CollectionModel<EntityModel<Location>> all(){
        List<EntityModel<Location>> locations = service.getLocations();

        return new CollectionModel<>(locations,
                linkTo(methodOn(LocationController.class).all()).withSelfRel());
    }

    /**
     * Method to retrieve only one location by id
     * @param id
     * @return EntityModel object with the retrieved Location
     */
    @GetMapping("/cities/{id}")
    EntityModel<Location> one(@PathVariable Long id) {
        Location location = service.getLocation(id);

        return assembler.toModel(location);
    }

    /**
     * Method to retrieve all the suggestions matching the query provided
     * @param q
     * @return ColletionModel<EntityModel<Location>> containing the Entitiy objects with the resulting Locations
     */
    @GetMapping(value = "/suggestions", params= "q")
    CollectionModel<EntityModel<Location>> allSugestions(@RequestParam String q){
        List<EntityModel<Location>> locations = service.getSuggestedLocations(q);

        return new CollectionModel<>(locations,
                linkTo(methodOn(LocationController.class).all()).withSelfRel());
    }

}
