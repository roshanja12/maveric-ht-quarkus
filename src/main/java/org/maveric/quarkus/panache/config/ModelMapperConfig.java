package org.maveric.quarkus.panache.config;

import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import org.modelmapper.ModelMapper;

@Singleton
public class ModelMapperConfig {

    @Produces
    public ModelMapper modelMapper()
    {
        return new ModelMapper();
    }
}
