package be.rebero.demo.services;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.soap.client.SoapFaultClientException;
import org.tempuri.Add;
import org.tempuri.AddResponse;
import org.tempuri.Calculator;
import org.tempuri.ObjectFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.util.JAXBSource;
import javax.xml.soap.SOAPException;
import javax.xml.transform.dom.DOMSource;

import java.util.HashMap;
import java.util.Map;

import static org.apache.camel.component.spring.ws.SpringWebserviceConstants.SPRING_WS_SOAP_ACTION;

@Service
public class TestService {

    @Autowired
    private CamelContext camelContext;

    public int addNumbers(int a, int b) throws JAXBException {

        ProducerTemplate producerTemplate = camelContext.createProducerTemplate();

        Add add = new Add();
        add.setIntA(a);
        add.setIntB(b);

        JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);

        Map<String, Object> headers = new HashMap<>();
        headers.put(SPRING_WS_SOAP_ACTION, "http://tempuri.org/Add");
//        headers.put(SPRING_WS_SOAP_ACTION, "http://tempuri.org/Calculator");
        producerTemplate.sendBodyAndHeaders("direct:camel-ws", new JAXBSource(context, add), headers);
        return 0;
    }
}
