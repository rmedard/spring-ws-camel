package be.rebero.demo.services;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.stereotype.Component;
import org.tempuri.Add;

@Component
public class CamelRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        CamelContext camelContext = new DefaultCamelContext();
        from("direct:camel-ws")
//                .process(exchange -> {
//            exchange.getIn().setBody();
//        })
//                .log("Got message: ${body}")
                .routeId("test-route-id")
                .to("spring-ws:http://www.dneonline.com/calculator.asmx");
    }
}
