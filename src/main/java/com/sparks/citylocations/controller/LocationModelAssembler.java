package com.sparks.citylocations.controller;

import com.sparks.citylocations.model.Location;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Implementation of EntityModel list to comply with HATEOAS in the API
 */
@Component
public class LocationModelAssembler  implements RepresentationModelAssembler<Location, EntityModel<Location>> {

    /**
     * Method to receive a location and return an entity with proper link implementation for self and the general collection
     * @param location
     * @return
     */
    @Override
    public EntityModel<Location> toModel(Location location) {
        return new EntityModel<Location>(location,
                linkTo(methodOn(LocationController.class).one(location.getId())).withSelfRel(),
                linkTo(methodOn(LocationController.class).all()).withRel("locations"));
    }
}
