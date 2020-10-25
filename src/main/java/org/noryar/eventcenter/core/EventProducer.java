package org.noryar.eventcenter.core;

/**
 * 事件生成器
 *
 * @author noryar
 */
public interface EventProducer<D, E extends Event> {

    /**
     * producer的唯一标识
     *
     * @return uuid.
     * @author liliang
     */
    String getUuid();

    /**
     * 生成并分发事件
     *
     * @param oriData 事件生成所需数据
     * @param preContext 前一个事件的上下文
     * @return event
     * @throws EventException exception
     * @author liliang
     */
    E produceAndDispatch(D oriData, EventContext preContext) throws EventException;

    /**
     * 设置分发器，一般在produce成功后刁青dispatcher进行分发.
     *
     * @param dispatcher dispatcher
     * @throws EventException exception
     */
    void setDispatcher(EventDispatcher dispatcher) throws EventException;
}
