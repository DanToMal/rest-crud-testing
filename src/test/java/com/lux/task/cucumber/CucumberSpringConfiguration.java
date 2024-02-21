package com.lux.task.cucumber;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.DefaultDataTableCellTransformer;
import io.cucumber.java.DefaultDataTableEntryTransformer;
import io.cucumber.java.DefaultParameterTransformer;
import org.springframework.boot.test.context.SpringBootTest;

import io.cucumber.spring.CucumberContextConfiguration;

import java.lang.reflect.Type;

@CucumberContextConfiguration
@SpringBootTest
public class CucumberSpringConfiguration {
    private final ObjectMapper objectMapper;

    public CucumberSpringConfiguration() {
        this.objectMapper = new ObjectMapper();
    }

    @DefaultParameterTransformer
    @DefaultDataTableEntryTransformer
    @DefaultDataTableCellTransformer
    public Object transformer(Object fromValue, Type toValueType) {
        return objectMapper.convertValue(fromValue, objectMapper.constructType(toValueType));
    }
}