package be.rebero.demo.services;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tempuri.Add;
import org.tempuri.AddResponse;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.util.JAXBResult;
import javax.xml.namespace.QName;
import javax.xml.transform.Result;
import java.io.StringWriter;
import java.util.Map;

@Service
public class TestService {

    @Autowired
    private CamelContext camelContext;

    public int addNumbers(int a, int b) {

        ProducerTemplate producerTemplate = camelContext.createProducerTemplate();

        Add add = new Add();
        add.setIntA(a);
        add.setIntB(b);

        JAXBElement<Add> jaxbElement = new JAXBElement<>(new QName(Add.class.getSimpleName()), Add.class, add);

        try {
            JAXBContext context = JAXBContext.newInstance(Add.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            Result result = new JAXBResult(context);

//            StringWriter sw = new StringWriter();
            marshaller.marshal(add, result);
            producerTemplate.sendBody("direct:camel-ws", result);
//            producerTemplate.requestBody("direct:camel-ws", result);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return 1;
    }
}
