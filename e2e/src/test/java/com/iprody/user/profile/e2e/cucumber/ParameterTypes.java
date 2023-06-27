package com.iprody.user.profile.e2e.cucumber;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.DefaultDataTableCellTransformer;
import io.cucumber.java.DefaultDataTableEntryTransformer;
import io.cucumber.java.DefaultParameterTransformer;

import java.lang.reflect.Type;

/**
 * Class for convert parameters types from Scenario to methods in stepsDefinition.
 */
public class ParameterTypes {

    /**
     * ObjectMapper to convert Types.
     */
    private final ObjectMapper objectMapper;

    /**
     * Required constructor.
     * @param objectMapper ObjectMapper in DI
     */
    public ParameterTypes(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    /**
     * Service method for cucumber converting Types.
     * @param fromValue accepts Object from Scenario
     * @param toValueType out Type this value
     * @return Object
     */
    @DefaultParameterTransformer
    @DefaultDataTableEntryTransformer
    @DefaultDataTableCellTransformer
    public Object transformer(Object fromValue, Type toValueType) {
        return objectMapper.convertValue(fromValue, objectMapper.constructType(toValueType));
    }
}
