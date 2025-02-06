package com.example.teamcity.api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Step {
    private String id;
    private String name;
    @Builder.Default
    private String type = "simpleRunner";
}
