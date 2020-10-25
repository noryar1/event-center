package org.noryar.eventcenter.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 事件抽象
 *
 * @author noryar
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public abstract class Event<D> {
    private EventContext context;
    private EventContext preContext;
    private D extendData;
}
