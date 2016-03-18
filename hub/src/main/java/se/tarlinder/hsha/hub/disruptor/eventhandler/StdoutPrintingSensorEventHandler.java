package se.tarlinder.hsha.hub.disruptor.eventhandler;

import com.lmax.disruptor.EventHandler;
import se.tarlinder.hsha.hub.event.SensorEvent;

public class StdoutPrintingSensorEventHandler implements EventHandler<SensorEvent> {

    @Override
    public void onEvent(SensorEvent event, long sequence, boolean endOfBatch) {
        System.out.println(event);
    }
}
