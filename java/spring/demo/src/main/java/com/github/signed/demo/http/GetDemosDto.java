package com.github.signed.demo.http;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetDemosDto {
    public List<String> demos = new ArrayList<>();
}
