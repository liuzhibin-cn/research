package org.liuzhibin.research.disruptor;

import java.util.HashMap;
import java.util.Map;

/**
 * 支持EventHandler多线程处理的disruptor事件对象。
 * 
 * <p>
 * 某些场景下，需要对某个EventHandler开启多线程并行处理，加快处理速度。<br />
 * 开启多线程后，事件只允许被EventHandler处理1次。</p>
 * <p>
 * 例如当前ringbuffer中有待处理的事件e3, e4, e5, e6 ...，某个EventHandler开启2个线程并行处理，t1正在处理e3事件，
 * 此时t2刚处理完毕事件e2，申请下一个事件得到e3，发现t1正在处理e3，则继续申请下一个事件e4，并开始处理e4。
 * 同理，当t1处理完e3后，可以接着处理e5，但如果t2比t1更早完成处理，则由t2接着处理e5。
 * </p>
 * 
 * @author Richie 刘志斌 yudi@sina.com
 * Nov 17, 2016
 */
public class CompetitiveEvent<D> {
    /**
     * 事件数据。
     */
    private D data;
    /**
     * 
     */
    private Map<String, String> competitors;
    
    public void setData(D value) {
        this.data = value;
    }
    public D getData() {
        return this.data;
    }
    
    public boolean isCompleted(String group) {
        return this.competitors!=null && this.competitors.containsKey(group);
    }
    public boolean compete(String group, String name) {
        if(isCompleted(group)) return false;
        synchronized (this) {
            if(isCompleted(group)) return false;
            this.competitors = new HashMap<String, String>(1);
            this.competitors.put(group, name);
            return true;
        }
    }
    public void resetCompetitiveState() {
        if(this.competitors==null) return;
        this.competitors.clear();
    }
}