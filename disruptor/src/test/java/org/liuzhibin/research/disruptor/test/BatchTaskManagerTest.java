package org.liuzhibin.research.disruptor.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.liuzhibin.research.disruptor.BatchTaskManager;
import org.liuzhibin.research.disruptor.CompetitiveEvent;
import org.liuzhibin.research.disruptor.EventHandlerDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BatchTaskManagerTest {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private SimpleDateFormat format = new SimpleDateFormat("ss:SSS");
    
    @SuppressWarnings("unchecked") 
    @Test
    public void testBatchTask() {
        BatchTaskManager<Order, CompetitiveEvent<Order>> manager = 
                new BatchTaskManager<Order, CompetitiveEvent<Order>>(CompetitiveEvent::new);
        
        manager.handleEventsWith(
                new EventHandlerDescriptor<Order, CompetitiveEvent<Order>>(
                    (CompetitiveEvent<Order> event, long sequence, boolean endOfBatch) -> 
                    {
                        Date start = new Date();
                        Thread.sleep(100);
                        log.info("> [" + format.format(start) + "->" + format.format(new Date()) + "] " + event.getData().getId() + ": Extract stock info from qq.com");
                    }
                        , "ex-sto-info", 2)
            ).then(
                new EventHandlerDescriptor<Order, CompetitiveEvent<Order>>(
                    (CompetitiveEvent<Order> event, long sequence, boolean endOfBatch) -> 
                    {
                        Date start = new Date();
                        Thread.sleep(50);
                        log.info("> [" + format.format(start) + "->" + format.format(new Date()) + "] " + event.getData().getId() + ": Update database");
                    })
            ).then(
                new EventHandlerDescriptor<Order, CompetitiveEvent<Order>>(
                    (CompetitiveEvent<Order> event, long sequence, boolean endOfBatch) -> 
                    {
                        Date start = new Date();
                        event.resetCompetitiveState();
                        log.info("> [" + format.format(start) + "->" + format.format(new Date()) + "] " + event.getData().getId() + ": Reset competitive state");
                    })
            );
        
        int count = 30;
        List<Order> data = new ArrayList<Order>(count);
        for(int i=0; i<count; i++) data.add(new Order());
        
        manager.start(data);
        manager.shutdown();
    }
}