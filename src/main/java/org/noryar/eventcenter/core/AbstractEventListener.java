package org.noryar.eventcenter.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 1. 自动获取监听事件类型
 * 2. 代理事件生成工厂
 * 3. 注入默认的事件中心
 *
 * 注意：集成该类的子类必须显示声明对EventListener的实现，否则无法获取到监听事件的类型，也无法注册到事件中心
 * @param <E>
 *
 * @author noryar
 */
@Slf4j
public abstract class AbstractEventListener<E extends Event> implements EventListener<E> {
    private EventProducerFactory producerFactory;

    @Override
    public Class<? extends Event> getEventType() {
        Type[] genericInterfaces = getClass().getGenericInterfaces();
        for (Type type : genericInterfaces) {
            if ((type instanceof ParameterizedType)
                    && ((ParameterizedType) type).getRawType().equals(EventListener.class)) {
                return (Class<? extends Event>) ((ParameterizedType) type).getActualTypeArguments()[0];
            }
        }
        return null;
    }

    @Override
    public void setProducerFactory(EventProducerFactory producerFactory) {
        this.producerFactory = producerFactory;
    }

    @Override
    @Async
    public void onEvent(E event) throws EventException {
        log.info("start hand event, eventObj[{}], listener[{}]",
                event.getClass().getSimpleName(), this.getClass().getSimpleName());
        executeAsyncOnEvent(event);
    }

    protected abstract void executeAsyncOnEvent(E event) throws EventException;
}
