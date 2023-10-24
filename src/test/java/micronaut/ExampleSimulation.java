package micronaut;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class ExampleSimulation extends Simulation {
    private HttpProtocolBuilder httpProtocol = http.baseUrl("https://reqres.in/api");

    private ScenarioBuilder FetchUsers = scenario("Get API Request Demo").exec(
            http("Get all User")
                    .get("/users/2")
                    .check(status().is(200)).
                    check(jsonPath("$.data.first_name").is("Janet"))).pause(1);


    private ScenarioBuilder CreateUsers = scenario("Create User").exec(
            http("Create a new User")
                    .post("/users").header("content-type","application/json")
                    .asJson()
                    .body(StringBody("{\n" +
                            "    \"name\": \"QaAutomationHub\",\n" +
                            "    \"job\": \"leader\"\n" +
                            "}")).asJson()
                    .check(status().is(201),jsonPath("$.name").is("QaAutomationHub"))).pause(1);

//    private ScenarioBuilder Upateusers = scenario("Update User").exec(
//            http("Update the User")
//                    .put("/users/2").header("content-type","application/json")
//                    .asJson().body(RawFileBody("Data/user.json"))
//                    .check(status().is(200),jsonPath("$.name").is("qaautomationhub"));

    {
        setUp(
//                FetchUsers.injectOpen(atOnceUsers(30))
                FetchUsers.injectOpen(constantUsersPerSec(300).during(60))
        ).protocols(httpProtocol);
    }

}
