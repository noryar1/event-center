package org.noryar.eventcenter.core;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 事件中心自动装配
 *
 * @author noryar
 */
@Configuration
@ConditionalOnProperty(name = "event-center.open", havingValue = "true")
public class EventCenterAutoConfig {

    @Bean("defaultEventDispatcher")
    public DefaultEventDispatcher getDefaultEventDispatcher(List<EventProducer> producers,
                                                            List<EventListener> listeners) throws EventException {
        DefaultEventDispatcher defaultEventDispatcher = new DefaultEventDispatcher();
        defaultEventDispatcher.init(producers, listeners);
        return defaultEventDispatcher;
    }

}
