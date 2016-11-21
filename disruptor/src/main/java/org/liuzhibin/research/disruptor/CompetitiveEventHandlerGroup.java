package org.liuzhibin.research.disruptor;

import java.util.List;

import com.lmax.disruptor.dsl.EventHandlerGroup;

/**
 * 对{@link EventHandlerGroup}的包装。
 * 
 * @author Richie 刘志斌 yudi@sina.com
 * Nov 17, 2016
 */
public class CompetitiveEventHandlerGroup<D, E extends CompetitiveEvent<D>> {
    
    private BatchTaskManager<D, E> manager;
    private EventHandlerGroup<E> group;
    
    public CompetitiveEventHandlerGroup(BatchTaskManager<D, E> manager, EventHandlerGroup<E> group) {
        this.manager = manager;
        this.group = group;
    }
    
    @SuppressWarnings({ "unchecked" })
    public CompetitiveEventHandlerGroup<D, E> handleEventsWith(EventHandlerDescriptor<D, E>... descriptors) {
        return new CompetitiveEventHandlerGroup<D, E>(manager, group.handleEventsWith(manager.buildEventHandler(descriptors)));
    }
    
    @SuppressWarnings("unchecked")
    public CompetitiveEventHandlerGroup<D, E> then(EventHandlerDescriptor<D, E>... descriptors) {
        return new CompetitiveEventHandlerGroup<D, E>(manager, group.then(manager.buildEventHandler(descriptors)));
    }
    
    @SuppressWarnings("unchecked")
    public CompetitiveEventHandlerGroup<D, E> addResetStateHandler() {
        return new CompetitiveEventHandlerGroup<D, E>(manager, group.then(
                (CompetitiveEvent<D> event, long sequence, boolean endOfBatch) -> 
                {
                    event.resetCompetitiveState();
                }
            ));
    }
    
    public BatchTaskManager<D, E> start(List<D> data) {
        return manager.start(data);
    }
}