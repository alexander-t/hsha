package se.tarlinder.hsha.hub.disruptor.eventhandler;

import com.lmax.disruptor.EventHandler;
import org.springframework.stereotype.Component;
import se.tarlinder.hsha.hub.event.ringbuffer.SuperEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Simple event handler that just remembers the last value of a device.
 */
@Component
public class DeviceValueRepository implements EventHandler<SuperEvent> {

    private Map<Integer, String> lastValues = new HashMap<>();

    @Override
    public void onEvent(SuperEvent event, long sequence, boolean endOfBatch) {
        if (event.isDeviceEvent()) {
            lastValues.put(event.id, event.value);
        }
    }

    public Optional<String> getLastValueFor(int sensorId) {
        return Optional.ofNullable(lastValues.get(sensorId));
    }
}
