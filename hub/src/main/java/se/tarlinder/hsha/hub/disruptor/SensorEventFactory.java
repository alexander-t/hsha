package se.tarlinder.hsha.hub.disruptor;

import com.lmax.disruptor.EventFactory;
import se.tarlinder.hsha.hub.event.SensorEvent;

public class SensorEventFactory implements EventFactory<SensorEvent> {

    @Override
    public SensorEvent newInstance() {
        return new SensorEvent();
    }
}
