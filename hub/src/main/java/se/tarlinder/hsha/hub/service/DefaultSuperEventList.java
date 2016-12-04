package se.tarlinder.hsha.hub.service;

import com.lmax.disruptor.EventHandler;
import se.tarlinder.hsha.hub.event.ringbuffer.SuperEvent;

import java.util.ArrayList;
import java.util.List;

public class DefaultSuperEventList implements SuperEventList {

    private List<EventHandler<SuperEvent>> handlers = new ArrayList<>();

    @Override
    public List<EventHandler<SuperEvent>> getHandlers() {
        return handlers;
    }

    public void addEventHandler(EventHandler<SuperEvent> eventHandler) {
        handlers.add(eventHandler);
    }
}
