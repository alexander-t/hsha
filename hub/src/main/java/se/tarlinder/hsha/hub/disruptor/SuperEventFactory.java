package se.tarlinder.hsha.hub.disruptor;

import com.lmax.disruptor.EventFactory;
import se.tarlinder.hsha.hub.event.ringbuffer.SuperEvent;

public class SuperEventFactory implements EventFactory<SuperEvent> {

    @Override
    public SuperEvent newInstance() {
        return new SuperEvent();
    }
}
