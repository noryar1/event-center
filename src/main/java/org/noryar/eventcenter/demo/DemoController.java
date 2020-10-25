package org.noryar.eventcenter.demo;


import org.noryar.eventcenter.core.EventContext;
import org.noryar.eventcenter.core.EventException;
import org.noryar.eventcenter.core.EventProducer;
import org.noryar.eventcenter.core.EventProducerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event-center/demo")
public class DemoController {

    @Autowired(required = false)
    private EventProducerFactory defaultEventDispatcher;

    @RequestMapping("/doEvent")
    public ResponseEntity doEvent() throws EventException {
        EventProducer demoProducer = defaultEventDispatcher.getProducer("DEMO_PRODUCER");
        demoProducer.produceAndDispatch(new Object(), new EventContext());
        return new ResponseEntity("do success!", HttpStatus.OK);
    }

}
