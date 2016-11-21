package org.liuzhibin.research.disruptor;

import com.lmax.disruptor.EventHandler;

public class EventHandlerDescriptor<D, E extends CompetitiveEvent<D>> {
    private String groupName;
    private EventHandler<E> handler;
    private int parallelism;
    
    public EventHandlerDescriptor(EventHandler<E> handler) {
        this(handler, null, 1);
    }
    public EventHandlerDescriptor(EventHandler<E> handler, String groupName, int parallelism) {
        this.groupName = groupName;
        this.handler = handler;
        this.parallelism = parallelism;
    }
    
    public String getGroupName() {
        return this.groupName;
    }
    public EventHandler<E> getHandler() {
        return this.handler;
    }
    public int getParallelism() {
        return this.parallelism;
    }
}