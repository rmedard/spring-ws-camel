package be.rebero.demo;

import org.apache.camel.Exchange;
import org.apache.camel.builder.DeadLetterChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.client.SoapFaultClientException;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

@Configuration
public class CamelRoutesConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(CamelRoutesConfig.class);

    @Bean
    public DeadLetterChannelBuilder errorHandlerBuilder() {
        return (DeadLetterChannelBuilder) new DeadLetterChannelBuilder("direct:error-ws")
                .disableRedelivery()
                .onExceptionOccurred(exchange -> {
                    if (exchange.getProperty(Exchange.EXCEPTION_CAUGHT) instanceof SoapFaultClientException) {
                        SoapFault soapFault = ((SoapFaultClientException)exchange.getProperty(Exchange.EXCEPTION_CAUGHT)).getSoapFault();
                        StringWriter writer = new StringWriter();
                        TransformerFactory.newInstance().newTransformer().transform(soapFault.getSource(), new StreamResult(writer));
                        LOGGER.error("Got fault exception: {}", writer.toString());
                    }
                });
    }
}
