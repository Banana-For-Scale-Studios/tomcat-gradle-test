package test;

import com.companyname.dirtylibs.restapi.TestHelperController;
import com.jayway.restassured.RestAssured;
import org.junit.Before;

import static com.jayway.restassured.RestAssured.get;

public class BaseIntegrationTest {
    @Before()
    public void setup() {
        RestAssured.baseURI = "http://localhost:8090";
        RestAssured.basePath = "rest/";
        get(TestHelperController.uriExtension + "/resetDatabase");
    }
}
