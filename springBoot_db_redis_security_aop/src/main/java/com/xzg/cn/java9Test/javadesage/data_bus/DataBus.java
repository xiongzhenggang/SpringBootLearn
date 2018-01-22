package com.xzg.cn.java9Test.javadesage.data_bus;

import java.util.HashSet;
import java.util.Set;

public class DataBus {

    private static final DataBus INSTANCE = new DataBus();

    private final Set<Member> listeners = new HashSet<>();

    public static DataBus getInstance() {
        return INSTANCE;
    }

    /**
     * Register a member with the data-bus to start receiving events.
     *
     * @param member The member to register
     */
    public void subscribe(final Member member) {
        this.listeners.add(member);
    }

    /**
     * Deregister a member to stop receiving events.
     *
     * @param member The member to deregister
     */
    public void unsubscribe(final Member member) {
        this.listeners.remove(member);
    }

    /**
     * Publish and event to all members.
     *
     * @param event The event
     */
    public void publish(final DataType event) {
        event.setDataBus(this);
        listeners.forEach(listener -> listener.accept(event));
    }
}