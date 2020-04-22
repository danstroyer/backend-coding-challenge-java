package com.sparks.citylocations.model;

import lombok.Data;
import org.springframework.boot.CommandLineRunner;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 */
@Data
@Entity
public class Location {
    private @Id @GeneratedValue Long id;
    private String name;
    private double latitude;
    private double longitude;
    private Float score;

    public Location(){}

    /**
     * Constructor for Location entity
     * @param id
     * @param name
     * @param latitude
     * @param longitude
     * @param score
     */
    public Location(Long id, String name, double latitude, double longitude, float score) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    /**
     * Location String representation
     * @return
     */
    @Override
    public String toString() {
        return "Location{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", score=" + score +
                '}';
    }

    /**
     * Location equal validation
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if(obj instanceof Location
            && (this.getName().equals(((Location) obj).getName()))
            && (this.getLatitude() == ((Location) obj).getLatitude())
            && (this.getLatitude() == ((Location) obj).getLongitude())){
            isEqual = true;
        }

        return isEqual;
    }

}
