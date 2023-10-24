package micronaut;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class MicronautGetSimulation extends Simulation {
    private HttpProtocolBuilder httpProtocol = http.baseUrl("http://localhost:8080/api/v1");

    private ScenarioBuilder FetchUsers = scenario("Get API Request Demo").exec(
            http("Get all User")
                    .get("/users")
                    .check(status().is(200))
//                    .check(jsonPath("$.data[0].username").is("ftQYQ"))
    ).pause(1);

    {
        setUp(
//                FetchUsers.injectOpen(atOnceUsers(30))
                FetchUsers.injectOpen(constantUsersPerSec(30).during(60))
        ).protocols(httpProtocol);
    }

}
