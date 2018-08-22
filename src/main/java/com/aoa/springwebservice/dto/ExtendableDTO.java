package com.aoa.springwebservice.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Builder;
import lombok.Singular;
import lombok.ToString;

import java.util.Map;

@Builder @ToString
public class ExtendableDTO {
    public String name;
    @Singular private Map<String, String> properties;

    @JsonAnySetter
    public void add(String key, String value) {
        properties.put(key, value);
    }
    @JsonAnyGetter
    public Map<String, String> getProperties() {
        return properties;
    }
}
