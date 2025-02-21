package com.example.teamcity.api.spec;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;

public class ValidationResponseSpecifications {

    static String ERROR_MESSAGE = "ID should start with a latin letter and contain only latin letters, digits and underscores (at most 225 characters).";

    public static ResponseSpecification checkProjectWithNameAlreadyExist(String projectName) {
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectStatusCode(SC_BAD_REQUEST);
        responseSpecBuilder.expectBody(Matchers.containsString(String.format("Project with this name already exists: %s", projectName)));
        return responseSpecBuilder.build();
    }

    public static ResponseSpecification checkProjectWithIdAlreadyExist(String projectId) {
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectStatusCode(SC_BAD_REQUEST);
        responseSpecBuilder.expectBody(Matchers.containsString(String.format("Project ID \"%s\" is already used by another project", projectId)));
        return responseSpecBuilder.build();
    }

    public static ResponseSpecification checkProjectWithIdWithTooLongId(String projectId) {
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectStatusCode(SC_INTERNAL_SERVER_ERROR);
        responseSpecBuilder.expectBody(Matchers.containsString(String.format("Project ID \"%s\" is invalid: it is 226 characters long while the maximum length is 225. " + ERROR_MESSAGE, projectId)));
        return responseSpecBuilder.build();
    }

    public static ResponseSpecification checkProjectWithIdWithNameStartWithNonLatinLetter(String projectId, String nonLatinLetter) {
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectStatusCode(SC_INTERNAL_SERVER_ERROR);
        responseSpecBuilder.expectBody(Matchers.containsString(String.format("Project ID \"%s\" is invalid: contains non-latin letter '%s'. " + ERROR_MESSAGE, projectId, nonLatinLetter)));
        return responseSpecBuilder.build();
    }
}
