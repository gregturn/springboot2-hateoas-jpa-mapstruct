package com.asimio.demo.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;

import com.asimio.demo.Application;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { Application.class })
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
@SqlGroup({
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = { "classpath:schema.sql", "classpath:data.sql" }),
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:purge.sql")
})
public class ActorControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        RestAssured.port = this.port;
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldRetrieve10ActorsForPage2LinksAndPageInfoJSON() {
        JsonPath jsonPath = RestAssured.
            given().
                accept(ContentType.JSON).
            when().
                get("/api/actors?page=2&size=10").
            then().
                statusCode(HttpStatus.OK.value()).
                contentType(ContentType.JSON).
                extract().jsonPath();

        List<Map<String, Object>> actualActors = (List<Map<String, Object>>) jsonPath.get("content");
        Assert.assertThat(actualActors.size(), Matchers.equalTo(10));

        List<Map<String, String>> actualLinks = (List<Map<String, String>>) jsonPath.get("links");
        Assert.assertThat(actualLinks.size(), Matchers.equalTo(5));
        this.assertLinks(actualLinks, "first", String.format("http://localhost:%s/api/actors?page=0&size=10", ActorControllerIntegrationTest.this.port));
        this.assertLinks(actualLinks, "prev", String.format("http://localhost:%s/api/actors?page=1&size=10", ActorControllerIntegrationTest.this.port));
        this.assertLinks(actualLinks, "next", String.format("http://localhost:%s/api/actors?page=3&size=10", ActorControllerIntegrationTest.this.port));
        this.assertLinks(actualLinks, "last", String.format("http://localhost:%s/api/actors?page=19&size=10", ActorControllerIntegrationTest.this.port));
        this.assertLinks(actualLinks, "self", String.format("http://localhost:%s/api/actors?page=2&size=10", ActorControllerIntegrationTest.this.port));

        Map<String, Integer> actualPage = (Map<String, Integer>) jsonPath.get("page");
        Assert.assertThat(actualPage.get("number"), Matchers.equalTo(2));
        Assert.assertThat(actualPage.get("size"), Matchers.equalTo(10));
        Assert.assertThat(actualPage.get("totalPages"), Matchers.equalTo(20));
        Assert.assertThat(actualPage.get("totalElements"), Matchers.equalTo(200));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldRetrieve10ActorsForPage2LinksAndPageInfoJSON_HAL() {
        JsonPath jsonPath = RestAssured.
            given().
                accept("application/hal+json").
            when().
                get("/api/actors?page=2&size=10").
            then().
                statusCode(HttpStatus.OK.value()).
                contentType(ContentType.JSON).
                extract().jsonPath();

        List<Map<String, Object>> actualActors = (List<Map<String, Object>>) jsonPath.get("_embedded.actors");
        Assert.assertThat(actualActors.size(), Matchers.equalTo(10));

        Map<String, Map<String, String>> actualLinks = (Map<String, Map<String, String>>) jsonPath.get("_links");
        Assert.assertThat(actualLinks.size(), Matchers.equalTo(5));
        this.assertLinks(actualLinks, "first", String.format("http://localhost:%s/api/actors?page=0&size=10", ActorControllerIntegrationTest.this.port));
        this.assertLinks(actualLinks, "prev", String.format("http://localhost:%s/api/actors?page=1&size=10", ActorControllerIntegrationTest.this.port));
        this.assertLinks(actualLinks, "next", String.format("http://localhost:%s/api/actors?page=3&size=10", ActorControllerIntegrationTest.this.port));
        this.assertLinks(actualLinks, "last", String.format("http://localhost:%s/api/actors?page=19&size=10", ActorControllerIntegrationTest.this.port));
        this.assertLinks(actualLinks, "self", String.format("http://localhost:%s/api/actors?page=2&size=10", ActorControllerIntegrationTest.this.port));

        Map<String, Integer> actualPage = (Map<String, Integer>) jsonPath.get("page");
        Assert.assertThat(actualPage.get("number"), Matchers.equalTo(2));
        Assert.assertThat(actualPage.get("size"), Matchers.equalTo(10));
        Assert.assertThat(actualPage.get("totalPages"), Matchers.equalTo(20));
        Assert.assertThat(actualPage.get("totalElements"), Matchers.equalTo(200));
    }

    @SuppressWarnings("serial")
    private void assertLinks(List<Map<String, String>> links, String relValue, String hrefValue) {
        Assert.assertThat(links, Matchers.hasItem(new HashMap<String, String>() {{
            put("rel", relValue);
            put("href", hrefValue);
        }}));
    }

    @SuppressWarnings("serial")
    private void assertLinks(Map<String, Map<String, String>> links, String relValue, String hrefValue) {
        Assert.assertThat(links, Matchers.hasEntry(relValue, new HashMap<String, String>() {{
            put("href", hrefValue);
        }}));
    }
}