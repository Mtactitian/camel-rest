package com.alexb.routes;

import com.alexb.exception.UserNotFoundException;
import com.alexb.model.User;
import com.alexb.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;
import static org.apache.camel.Exchange.EXCEPTION_CAUGHT;
import static org.apache.camel.Exchange.HTTP_RESPONSE_CODE;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserRoute extends RouteBuilder {

    private final UserService userService;

    @Override
    public void configure() {

        Predicate hasNoExceptions = ex -> isNull(ex.getIn().getHeader(EXCEPTION_CAUGHT));

        interceptFrom()
                .log(LoggingLevel.INFO, "intercepted");

        // @formatter:off
        rest("/users")
                .get()
                    .route()
                    .id("Get all users")
                    .bean(userService,"getAll")
                    .setHeader(CONTENT_TYPE,() -> APPLICATION_JSON_UTF8_VALUE) // other way doesn't work because of binding
                .endRest()
                .get("/{id}")
                    .route()
                    .id("Get user by id")
                    .setProperty("value", constant("value")) // pass a clazz
                    .bean(userService,"getById")
                .endRest()
                .post()
                    .type(User.class)
                    .route()
                    .id("Save user")
                    .bean(userService,"saveUser")
                .endRest()
                .delete()
                    .route()
                    .id("Delete user by id")
                    .bean(userService,"deleteAll")
                .endRest();

        onException(UserNotFoundException.class)
                .handled(true)
                .setBody(() -> null)
                .setHeader(HTTP_RESPONSE_CODE, HttpStatus.NOT_FOUND::value)
                .stop();

        onCompletion()
                .onWhen(hasNoExceptions)
                .process(ex -> {});
        // @formatter:on
    }
}


