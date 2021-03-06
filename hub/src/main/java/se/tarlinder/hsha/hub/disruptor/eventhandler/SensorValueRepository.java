package se.tarlinder.hsha.hub.disruptor.eventhandler;

import com.lmax.disruptor.EventHandler;
import org.springframework.stereotype.Component;
import se.tarlinder.hsha.hub.event.ringbuffer.SuperEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Simple event handler that just remembers the last value of a sensor.
 */
@Component
public class SensorValueRepository implements EventHandler<SuperEvent> {

    private Map<Integer, Map<String, String>> lastValues = new HashMap<>();

    @Override
    public void onEvent(SuperEvent event, long sequence, boolean endOfBatch) {
        if (event.isSensorEvent()) {
            Map<String, String> eventsPerSensorType = lastValues.computeIfAbsent(event.id, m -> new HashMap<>());
            eventsPerSensorType.put(event.dataType, event.value);
        }
    }

    public Optional<Map<String, String>> getLastValueFor(int sensorId) {
        return Optional.ofNullable(lastValues.get(sensorId));
    }
}
