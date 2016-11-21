package org.liuzhibin.research.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * 对应{@link CompetitiveEvent}的{@link EventHandler}。<br />
 * 
 * 如果某个{@link EventHandler}使用3个线程并行处理，则会为{@link EventHandler}创建3个{@link CompetitiveEventHandler}，
 * 在{@link CompetitiveEventHandler#onEvent(CompetitiveEvent, long, boolean) CompetitiveEventHandler.onEvent()}中
 * 保证任何一个事件只被{@link CompetitiveEventHandler}其中1个实例处理。
 * 
 * @author Richie 刘志斌 yudi@sina.com
 * Nov 17, 2016
 */
@SuppressWarnings("rawtypes")
public class CompetitiveEventHandler<E extends CompetitiveEvent> implements EventHandler<E> {
    private String group;
    private String name;
    private EventHandler<E> originHandler;
    
    public CompetitiveEventHandler(String group, String name, EventHandler<E> originHandler) {
        this.group = group;
        this.name = name;
        this.originHandler = originHandler;
    }

    @Override
    public void onEvent(E event, long sequence, boolean endOfBatch) throws Exception {
        if(!event.isCompleted(group) && event.compete(group, name)) {
            originHandler.onEvent(event, sequence, endOfBatch);
        }
    }

}