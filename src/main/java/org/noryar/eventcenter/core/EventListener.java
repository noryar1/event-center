package org.noryar.eventcenter.core;

/**
 * 事件监听器.
 *
 * @author noryar
 */
public interface EventListener<E extends Event> {

    /**
     * 获取该监听器能够处理的事件类型
     */
    Class<? extends Event> getEventType();

    /**
     * 设置事件生成器工厂
     *
     * @param producerFactory producer factory.
     */
    void setProducerFactory(EventProducerFactory producerFactory);

    /**
     * 处理事件
     *
     * @param event event
     * @throws EventException exception
     */
    void onEvent(E event) throws EventException;
}
