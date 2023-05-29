package com.example.memderadmin.api;

import com.example.memderadmin.config.H2DatabaseCleanUp;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseController {

    @LocalServerPort
    private int port;
    @Autowired
    private H2DatabaseCleanUp h2DatabaseCleanUp;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        h2DatabaseCleanUp.execute();
    }
}
