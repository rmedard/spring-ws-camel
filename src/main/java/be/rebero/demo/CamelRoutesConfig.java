package be.rebero.demo;

import org.apache.camel.Exchange;
import org.apache.camel.builder.DeadLetterChannelBuilder;
import org.apache.camel.processor.RedeliveryPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.WebServiceException;

@Configuration
public class CamelRoutesConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(CamelRoutesConfig.class);

    @Bean
    public DeadLetterChannelBuilder myErrorHandler() {
        DeadLetterChannelBuilder deadLetterChannelBuilder = new DeadLetterChannelBuilder();
        deadLetterChannelBuilder.setDeadLetterUri("direct:error");
        deadLetterChannelBuilder.setRedeliveryPolicy(new RedeliveryPolicy().disableRedelivery());
        deadLetterChannelBuilder.setUseOriginalMessage(true);
        deadLetterChannelBuilder.setFailureProcessor(exchange -> {
            if (exchange.getProperty(Exchange.EXCEPTION_CAUGHT) instanceof WebServiceException) {
                WebServiceException webServiceException = (WebServiceException) exchange.getProperty(Exchange.EXCEPTION_CAUGHT);
                LOGGER.error("Web Service Exception yabaye ", webServiceException);
            }
            System.out.println();
        });
        return deadLetterChannelBuilder;
    }
}
