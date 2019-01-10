package com.alexb;

import com.alexb.model.User;
import com.alexb.service.UserService;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class Route2Test {

    @MockBean
    UserService userService;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    public void contextLoads() throws Exception {

        when(userService.getById(eq("5c1f3d8d7b35b1073f397907"), any()))
                .thenReturn(new User("A", "ALEX", "S", 33.3));


        User user = testRestTemplate.getForObject("/users/5c1f3d8d7b35b1073f397907", User.class);

        assertAll(
                () -> assertEquals("ALEX", user.getName())
        );
    }

    @Configuration
    @EnableAutoConfiguration
    @ComponentScan(basePackages = {"com.alexb.routes", "com.alexb.config"})
    static class Config {

    }
}

