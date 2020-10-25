package org.noryar.eventcenter.core;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 默认事件分发中心
 *
 * @author noryar
 */
@Slf4j
public class DefaultEventDispatcher implements EventDispatcher, EventProducerFactory {

    private Gson gson = new Gson();
    private Map<String, EventProducer> producersOfEvent;
    private Map<String, Set<EventListener>> listenersOfEvent;

    public void init(List<EventProducer> producers, List<EventListener> listeners) throws EventException {
        if (CollectionUtils.isEmpty(producers) || CollectionUtils.isEmpty(listeners)) {
            throw new EventException("no producers or listeners detected!");
        }
        initProducers(producers);
        initListeners(listeners);
    }

    private void initListeners(List<EventListener> listeners) {
        this.listenersOfEvent = new HashMap<>();
        for (EventListener listener : listeners) {
            String eventClazzName = listener.getEventType().getName();
            if (StringUtils.isBlank(eventClazzName)) {
                log.error("no event listener {} can handle!", listener);
                continue;
            }
            if (!listenersOfEvent.containsKey(eventClazzName)) {
                listenersOfEvent.put(eventClazzName, new HashSet<>());
            }
            listenersOfEvent.get(eventClazzName).add(listener);
            listener.setProducerFactory(this);
        }
    }

    private void initProducers(List<EventProducer> producers) throws EventException {
        this.producersOfEvent = new HashMap<>();
        for (EventProducer producer : producers) {
            producer.setDispatcher(this);
            String uuid = producer.getUuid();
            if (StringUtils.isBlank(uuid) || this.producersOfEvent.containsKey(uuid)) {
                throw new EventException("duplicate producer uuid @ " + uuid);
            }
            this.producersOfEvent.put(uuid, producer);
        }
    }

    @Override
    public void dispatch(Event event) {
        if (event == null || event.getContext() == null || this.listenersOfEvent == null) {
            log.error("empty event received!");
            return;
        }
        String eventClazzName = event.getClass().getName();
        log.debug("start dispatch event: {}, detail: {}", eventClazzName, gson.toJson(event));
        if (!listenersOfEvent.containsKey(eventClazzName)) {
            log.error("can not handle event: {}", eventClazzName);
            return;
        }

        for (EventListener listener : listenersOfEvent.get(eventClazzName)) {
            try {
                listener.onEvent(event);
            } catch (Exception e) {
                log.error("dispatch {} to {} error, e: ", event, listener, e);
            }
        }
        log.debug("end dispatch event: {}", eventClazzName);
    }

    @Override
    public EventProducer getProducer(String uuid) {
        if (StringUtils.isBlank(uuid) || MapUtils.isEmpty(this.producersOfEvent)) {
            return null;
        }
        return this.producersOfEvent.get(uuid);
    }
}
