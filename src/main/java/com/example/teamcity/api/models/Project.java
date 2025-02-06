package com.example.teamcity.api.models;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Project extends BaseModel{
    String parentProject;
    String id;
    String name;
    @Builder.Default
    String locator = "_Root";
    Boolean copyAllAssociatedSettings;

}
