package com.example.teamcity.api;

import com.example.teamcity.BaseTest;
import com.example.teamcity.api.generators.RandomData;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.requests.unchecked.UncheckedBase;
import com.example.teamcity.api.spec.Specifications;
import com.example.teamcity.api.spec.ValidationResponseSpecifications;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static com.example.teamcity.api.enums.Endpoint.PROJECTS;
import static com.example.teamcity.api.enums.Endpoint.USERS;
import static com.example.teamcity.api.generators.RandomData.MAX_LENGTH;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;

@Test(groups = {"Regression"})
public class ProjectTest extends BaseTest {

    @Test(description = "User should be able to create project", groups = {"Positive", "CRUD"})
    public void userCreatesProjectSuccess() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        var createdProject = userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        softy.assertEquals(testData.getProject().getName(), createdProject.getName());
    }

    @Test(description = "User should not be able to create two project with the same name", groups = {"Negative", "CRUD"})
    public void userCreatesSameNameProjectUnSuccess() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        var createdProject = userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());
        createdProject.setId(RandomData.getString(MAX_LENGTH));

        new UncheckedBase(Specifications.authSpec(testData.getUser()), PROJECTS)
                .create(createdProject)
                .then().spec(ValidationResponseSpecifications.checkProjectWithNameAlreadyExist(testData.getProject().getName()));
    }

    @Test(description = "User should not be able to create two project with the same id", groups = {"Negative", "CRUD"})
    public void userCreatesSameIdProjectUnSuccess() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        var createdProject = userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());
        createdProject.setName(RandomData.getString(MAX_LENGTH));

        new UncheckedBase(Specifications.authSpec(testData.getUser()), PROJECTS)
                .create(createdProject)
                .then().spec(ValidationResponseSpecifications.checkProjectWithIdAlreadyExist(testData.getProject().getId()));
    }

    @Test(description = "User should not be able to create project with too long id", groups = {"Negative", "CRUD"})
    public void userCreatesProjectWithTooLongIdUnSuccess() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        Project project = testData.getProject();
        project.setId(RandomData.getString("t", 225));

        new UncheckedBase(Specifications.authSpec(testData.getUser()), PROJECTS)
                .create(project)
                .then().spec(ValidationResponseSpecifications.checkProjectWithIdWithTooLongId(testData.getProject().getId()));
    }

    @Test(description = "User should not be able to create project with empty id", groups = {"Negative", "CRUD"})
    public void userCreatesProjectWithEmptyIdUnSuccess() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        Project project = testData.getProject();
        project.setId(RandomData.getString("", 0));

        new UncheckedBase(Specifications.authSpec(testData.getUser()), PROJECTS)
                .create(project)
                .then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR) // здесь баг, должно быть 400 вместо 500
                .body(Matchers.containsString("Project ID must not be empty."));
    }

    @Test(description = "User should not be able to create project starts with non latin letter", groups = {"Negative", "CRUD"})
    public void userCreatesProjectWithIdStartWithNonLatinLetterUnSuccess() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        Project project = testData.getProject();
        String nonLatinLetter = "Ю";
        project.setId(RandomData.getString(nonLatinLetter, 10));

        new UncheckedBase(Specifications.authSpec(testData.getUser()), PROJECTS)
                .create(project)
                .then().spec(ValidationResponseSpecifications.checkProjectWithIdWithNameStartWithNonLatinLetter(testData.getProject().getId(), nonLatinLetter));
    }

    @Test(description = "User should not be able to create project contains non latin letter", groups = {"Negative", "CRUD"})
    public void userCreatesProjectWithIdContainsNonLatinLetterUnSuccess() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        Project project = testData.getProject();
        String nonLatinLetter = "Ы";
        project.setId(RandomData.getString("test_" + nonLatinLetter, 10));

        new UncheckedBase(Specifications.authSpec(testData.getUser()), PROJECTS)
                .create(project)
                .then().spec(ValidationResponseSpecifications.checkProjectWithIdWithNameStartWithNonLatinLetter(testData.getProject().getId(), nonLatinLetter));
    }
}
