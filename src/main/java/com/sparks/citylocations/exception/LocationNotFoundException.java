package com.sparks.citylocations.exception;

public class LocationNotFoundException extends RuntimeException {
    public LocationNotFoundException(Long id){super("Could not find city id: " + id);}
}
