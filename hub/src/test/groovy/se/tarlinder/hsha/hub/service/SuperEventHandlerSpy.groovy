package se.tarlinder.hsha.hub.service

import com.lmax.disruptor.EventHandler
import se.tarlinder.hsha.hub.event.ringbuffer.SuperEvent

class SuperEventHandlerSpy implements EventHandler<SuperEvent> {

    public List<SuperEvent> recordedEvents = []

    @Override
    void onEvent(SuperEvent event, long sequence, boolean endOfBatch) {
        recordedEvents << event
    }
}
