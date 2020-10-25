package org.noryar.eventcenter.core;

/**
 * 事件分发器接口
 *
 * @author noryar
 */
public interface EventDispatcher {

    void dispatch(Event event);
}
