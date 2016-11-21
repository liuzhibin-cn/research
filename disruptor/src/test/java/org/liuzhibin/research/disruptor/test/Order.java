package org.liuzhibin.research.disruptor.test;

public class Order {
    private static int INIT_ID = 1000;
    
    private int id;
    
    public Order() {
        this.id = INIT_ID++;
    }
    
    public int getId() {
        return this.id;
    }
}