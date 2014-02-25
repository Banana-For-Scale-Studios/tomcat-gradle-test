package com.companyname.dirtylibs.persistence;

import com.companyname.dirtylibs.restapi.UserController;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.*;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

@Entity
@Configurable(autowire = Autowire.BY_TYPE, preConstruction = true)
public class User {
    @Id
    @GeneratedValue
    @JsonProperty
    private int id;
    @JsonProperty
    @Column(unique = true)
    private String username;
    @JsonProperty
    private String password;
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    @Transient
    public SecureRandom test;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    protected User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Integer> serializeToRestApi() throws JsonProcessingException {
        Response response = RestAssured.given()
                .authentication()
                .basic(username, password)
                .formParam("username", username)
                .formParam("password", password)
                .post(UserController.uriExtension);

        id = response.then().extract().jsonPath().get("id");
        return Arrays.asList(id);
    }
}
