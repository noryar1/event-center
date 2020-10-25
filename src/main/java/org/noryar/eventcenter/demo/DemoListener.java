package org.noryar.eventcenter.demo;

import lombok.extern.slf4j.Slf4j;
import org.noryar.eventcenter.core.AbstractEventListener;
import org.noryar.eventcenter.core.EventContext;
import org.noryar.eventcenter.core.EventException;
import org.noryar.eventcenter.core.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DemoListener extends AbstractEventListener<DemoEvent> implements EventListener<DemoEvent> {

    @Override
    protected void executeAsyncOnEvent(DemoEvent event) throws EventException {
        EventContext context = event.getContext();
        // 过滤当前类型不能由此listener消费
        log.info("handle event, context: {}", context);
    }
}
