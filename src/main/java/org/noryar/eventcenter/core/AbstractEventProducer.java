package org.noryar.eventcenter.core;

import lombok.AllArgsConstructor;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;

/**
 * 统一处理分发的过程
 * @param <D>
 * @param <E>
 *
 * @author noryar
 */
public abstract class AbstractEventProducer<D, E extends Event> implements EventProducer<D, E> {

    protected EventDispatcher dispatcher;

    @Override
    public E produceAndDispatch(D oriData, EventContext preContext) throws EventException {
        Assert.notNull(dispatcher, "no dispatcher detected!");
        E event = produce(oriData, preContext);
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new SyncEventAdapter(event));
        } else {
            dispatcher.dispatch(event);
        }
        return event;
    }

    @AllArgsConstructor
    class SyncEventAdapter extends TransactionSynchronizationAdapter {
        private E event;

        @Override
        public void afterCommit() {
            dispatcher.dispatch(event);
        }
    }

    @Override
    public void setDispatcher(EventDispatcher dispatcher) throws EventException {
        this.dispatcher = dispatcher;
    }

    protected abstract E produce(D oriData, EventContext preContext) throws EventException;
}
