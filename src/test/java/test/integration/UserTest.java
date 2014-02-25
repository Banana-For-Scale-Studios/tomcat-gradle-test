package test.integration;

import com.companyname.dirtylibs.persistence.User;
import com.companyname.dirtylibs.restapi.UserController;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import test.BaseIntegrationTest;

import java.util.Arrays;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasValue;

public class UserTest extends BaseIntegrationTest {

    @Test
    public void users_must_be_created_with_a_username_and_password() throws JsonProcessingException {
        new User("Amy", "Password")
                .serializeToRestApi();

        get(UserController.uriExtension).then()
                .body("[0]", hasValue("Amy"))
                .body("[0]", hasValue("Password"))
                .body("", hasSize(1));
    }

    @Test
    public void create_multiple_users() throws JsonProcessingException {


        for(User user: Arrays.asList(
                new User("Amy", "Password"),
                new User("Joe", "Password"),
                new User("Mike", "password"),
                new User("Anna", "Word of pass!")
        )) {
            user.serializeToRestApi();
        }

        get(UserController.uriExtension).then()
                .body("[0]", hasValue("Amy"))
                .body("[0]", hasValue("Password"))
                .body("[1]", hasValue("Joe"))
                .body("[1]", hasValue("Password"))
                .body("[2]", hasValue("Mike"))
                .body("[2]", hasValue("password"))
                .body("[3]", hasValue("Anna"))
                .body("[3]", hasValue("Word of pass!"))
                .body("", hasSize(4));
    }
}
