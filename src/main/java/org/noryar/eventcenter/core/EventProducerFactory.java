package org.noryar.eventcenter.core;

/**
 * 事件生成器工厂，提供获取生成器的接口
 *
 * @author noryar
 */
public interface EventProducerFactory {

    /**
     * 根据唯一id获取事件生成器
     *
     * @param uuid uuid
     * @return producer
     *
     * @author liliang
     */
    EventProducer getProducer(String uuid);
}
