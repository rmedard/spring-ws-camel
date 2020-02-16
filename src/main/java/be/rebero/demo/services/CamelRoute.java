package be.rebero.demo.services;

import org.apache.camel.builder.ErrorHandlerBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CamelRoute extends RouteBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(CamelRoute.class);

    @Autowired
    @Qualifier(value = "errorHandlerBuilder")
    private ErrorHandlerBuilder errorHandlerBuilder;

    @Override
    public void configure() {

        errorHandler(errorHandlerBuilder);

        from("direct:camel-ws")
                .routeId("test-route-id")
                .log("Got request: ${body}")
                .to("spring-ws:http://www.dneonline.com/calculator.asmx")
                .log("Got response: ${body}");

        from("direct:error-ws")
                .routeId("error-route-id")
                .log(">>> ${body}");
    }
}
