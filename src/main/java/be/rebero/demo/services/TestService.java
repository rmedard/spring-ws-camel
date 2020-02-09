package be.rebero.demo.services;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tempuri.Add;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.util.JAXBResult;
import javax.xml.namespace.QName;
import javax.xml.transform.Result;

@Service
public class TestService {

    @Autowired
    private CamelRoute camelRoute;

    @Autowired
    private ProducerTemplate template;

    public int addNumbers(int a, int b) {
        Add add = new Add();
        add.setIntA(a);
        add.setIntB(b);

        JAXBElement<Add> jaxbElement = new JAXBElement<>(new QName(Add.class.getSimpleName()), Add.class, add);

        try {
            JAXBContext context = JAXBContext.newInstance(Add.class);
            Result result = new JAXBResult(context);
            context.createMarshaller().marshal(jaxbElement, result);
            template.sendBody("direct:example", result);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return 1;
    }
}
