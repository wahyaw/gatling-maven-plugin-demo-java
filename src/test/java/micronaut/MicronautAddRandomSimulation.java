package micronaut;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class MicronautAddRandomSimulation extends Simulation {
    private HttpProtocolBuilder httpProtocol = http.baseUrl("http://localhost:8080/api/v1");

    private ScenarioBuilder CreateUsers = scenario("Create User").exec(
            http("Create a new User")
                    .get("/dad").header("content-type","application/json")
                    .asJson()
                    .check(status().is(200))
    ).pause(1);


    {
        setUp(
                CreateUsers.injectOpen(constantUsersPerSec(30).during(10))
        ).protocols(httpProtocol);
    }

}
