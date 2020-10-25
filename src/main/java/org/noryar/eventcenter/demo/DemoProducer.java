package org.noryar.eventcenter.demo;

import com.google.gson.JsonObject;
import org.noryar.eventcenter.core.*;
import org.springframework.stereotype.Component;

@Component
public class DemoProducer extends AbstractEventProducer<Object, DemoEvent> {

    @Override
    protected DemoEvent produce(Object oriData, EventContext preContext) throws EventException {
        EventContext eventContext = new EventContext();
        eventContext.setId(1L);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("msg", "hello world!");
        eventContext.setData(jsonObject);
        DemoEvent demoEvent = new DemoEvent();
        demoEvent.setContext(eventContext);
        return demoEvent;
    }

    @Override
    public String getUuid() {
        return "DEMO_PRODUCER";
    }
}
