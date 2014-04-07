package de.mwiesner.mydroid.events;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;


public final class EventBus {
    private static final Bus eventBus = new Bus(ThreadEnforcer.ANY);

    public static Bus getEventBus() {
        return eventBus;
    }
}