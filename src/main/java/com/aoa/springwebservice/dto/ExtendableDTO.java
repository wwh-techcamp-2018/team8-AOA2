package com.aoa.springwebservice.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString @NoArgsConstructor
public class ExtendableDTO {
    public String name;
    @Singular private Map<String, Object> properties = new HashMap<>();

    @JsonAnySetter
    public void add(String key, Object value) {
        properties.put(key, value);
    }
    @JsonAnyGetter
    public Map<String, Object> getProperties() {
        return properties;
    }
}
