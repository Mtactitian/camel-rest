package com.alexb.routes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
@Slf4j
public class AggregationRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        rest("/aggregate")
                .post()
                .type(StringInputWrapper.class)
                .route()
                .id("Aggregation route")
                .wireTap("direct:asd");


        from("direct:asd")
                .log(LoggingLevel.INFO, "received")
                .aggregate(header("X-Correlation-Id"))
                .strategy((oldExchange, newExchange) -> {
                    if (isNull(oldExchange)) {
                        String value = newExchange.getIn().getBody(StringInputWrapper.class)                                .getValue();
                        newExchange.getIn().setBody(value);
                        return newExchange;
                    } else {
                        String oldExchangeBody = oldExchange.getIn().getBody(String.class);
                        String newExhcangeBody = newExchange.getIn().getBody(StringInputWrapper.class).getValue();
                        oldExchange.getIn().setBody(oldExchangeBody + newExhcangeBody);
                        return oldExchange;
                    }
                })
                .completionPredicate(method(this, "isReady"))
                .to("log:aggregated");

    }

    public boolean isReady(String value) {
        return value.length() == 3;
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class StringInputWrapper {
    private String value;
}
