package be.rebero.demo.services;

import org.apache.camel.builder.ErrorHandlerBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CamelRoute extends RouteBuilder {

    @Autowired
    @Qualifier(value = "myErrorHandler")
    private ErrorHandlerBuilder channelBuilder;

    @Override
    public void configure() {

        errorHandler(channelBuilder);

        from("direct:camel-ws")
                .routeId("test-route-id")
                .log("Got request: ${body}")
                .to("spring-ws:http://www.dneonline.com/calculator.asmx")
                .log("Got response: ${body}");

        from("direct:error-ws")
                .routeId("error-route-id")
                .log("Got error: ${body}")
                .to("log:error");
    }
}
