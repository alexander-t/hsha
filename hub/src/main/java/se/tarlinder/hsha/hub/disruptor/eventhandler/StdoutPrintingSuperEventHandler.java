package se.tarlinder.hsha.hub.disruptor.eventhandler;

import com.lmax.disruptor.EventHandler;
import se.tarlinder.hsha.hub.event.ringbuffer.SuperEvent;

public class StdoutPrintingSuperEventHandler implements EventHandler<SuperEvent> {

    @Override
    public void onEvent(SuperEvent event, long sequence, boolean endOfBatch) {
        System.out.println(event);
    }
}
