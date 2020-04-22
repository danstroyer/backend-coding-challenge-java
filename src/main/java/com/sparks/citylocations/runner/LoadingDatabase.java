package com.sparks.citylocations.runner;

import com.sparks.citylocations.model.Location;
import com.sparks.citylocations.repository.LocationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;


/**
 * Loader service to populate database
 */
@Configuration
@Slf4j
public class LoadingDatabase {

    @Bean
    CommandLineRunner initDatabase(LocationRepository locationRepository){
        return args -> {
            log.info("--- LoadingDatabase::initDatabase() ---");
            log.info("Starting locations creation");

            BufferedReader tsvReader = null;
            try{
                // retrieve content of the tsv file
                Resource resource = new ClassPathResource("cities_canada-usa.tsv");
                Scanner scanner = new Scanner(resource.readableChannel());

                Boolean isFirstLine = true;

                // lets parse the content skipping the header values
                while(scanner.hasNextLine()){
                    String line = scanner.nextLine();

                    if(!isFirstLine){
                        String[] columns = line.split("\t");

                        //TODO
                        //implementation can be improved with third party libraries like OpenCSV integrated on spring-boot
                        //Had issues applying the tab separator, using at the moment a cheap shot to implement basic values
                        //Refactor can include proper object creating with a dynamic fields handling
                        Location location = new Location();
                        location.setId(Long.valueOf(columns[0]));
                        location.setName(columns[1] + ", " + columns[10] + ", " + columns[8]);
                        location.setLatitude(Double.parseDouble(columns[4]));
                        location.setLongitude(Double.parseDouble(columns[5]));

                        locationRepository.save(location);

                        log.info("New location created: " + location);

                    }
                    isFirstLine = false;
                }

            }catch (IOException ex){
                log.error("Error reading file: " + ex.getMessage());
            }

        };
    }
}