package org.liuzhibin.research.disruptor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;

/**
 * 对{@link Disruptor}的包装。
 * 
 * @author Richie 刘志斌 yudi@sina.com
 * Nov 17, 2016
 */
public class BatchTaskManager<D, E extends CompetitiveEvent<D>> {
    private static int BUFFER_SIZE = 8;
    
    private Disruptor<E> disruptor;
    private ExecutorService executorService;
    
    @SuppressWarnings("deprecation")
    public BatchTaskManager(EventFactory<E> eventFactory) {
        executorService = Executors.newCachedThreadPool(DaemonThreadFactory.INSTANCE);
        this.disruptor = new Disruptor<E>(eventFactory, BUFFER_SIZE, executorService, ProducerType.SINGLE, new BlockingWaitStrategy());
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public EventHandler<E>[] buildEventHandler(EventHandlerDescriptor... descriptors) {
        List<EventHandler<E>> handlers = new ArrayList<EventHandler<E>>();
        for(int i=0; i<descriptors.length; i++) {
            if(descriptors[i].getParallelism()==1) {
                handlers.add(descriptors[i].getHandler());
            } else {
                for(int j=0; j<descriptors[i].getParallelism(); j++) {
                    handlers.add(new CompetitiveEventHandler(descriptors[i].getGroupName(), descriptors[i].getGroupName() + (j+1), descriptors[i].getHandler()));
                }
            }
        }
        EventHandler<E>[] handlersArray = new EventHandler[handlers.size()];
        for(int i=0; i<handlers.size(); i++) 
            handlersArray[i] = handlers.get(i);
        return handlersArray;
    }
    
    @SuppressWarnings("unchecked")
    public CompetitiveEventHandlerGroup<D, E> handleEventsWith(EventHandlerDescriptor<D, E>... descriptors) {
        EventHandler<E>[] handlers = buildEventHandler(descriptors);
        return new CompetitiveEventHandlerGroup<D, E>(this, disruptor.handleEventsWith(handlers));
    }
    
    @SuppressWarnings("unchecked")
    public CompetitiveEventHandlerGroup<D, E> after(EventHandlerDescriptor<D, E>... descriptors) {
        EventHandler<E>[] handlers = buildEventHandler(descriptors);
        return new CompetitiveEventHandlerGroup<D, E>(this, disruptor.after(handlers));
    }
    
    public BatchTaskManager<D, E> start(List<D> data) {
        this.disruptor.start();
        RingBuffer<E> ringBuffer = this.disruptor.getRingBuffer();
        for(D d : data) {
            ringBuffer.publishEvent((event, sequence, s) -> event.setData(s), d);
        }
        return this;
    }
    
    public BatchTaskManager<D, E> shutdown() {
        this.disruptor.shutdown();
        this.executorService.shutdown();
        try {
            this.executorService.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
        }
        return this;
    }
}