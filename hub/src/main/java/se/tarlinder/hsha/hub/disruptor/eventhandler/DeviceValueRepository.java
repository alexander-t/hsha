package se.tarlinder.hsha.hub.disruptor.eventhandler;

import com.lmax.disruptor.EventHandler;
import org.springframework.stereotype.Component;
import se.tarlinder.hsha.hub.event.ringbuffer.SuperEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Simple event handler that just remembers the last value of a device.
 */
@Component
public class DeviceValueRepository implements EventHandler<SuperEvent> {

    private Map<Integer, DeviceStatus> devices = new HashMap<>();

    @Override
    public void onEvent(SuperEvent event, long sequence, boolean endOfBatch) {
        if (event.isDeviceEvent()) {
            devices.put(event.id,
                    new DeviceStatus(event.id, event.deviceName, event.value));
        }
    }

    public Optional<DeviceStatus> getLastValueFor(int sensorId) {
        return Optional.ofNullable(devices.get(sensorId));
    }

    public List<DeviceStatus> getAll() {
        return devices.values().stream().sorted((ds1, ds2) -> ds1.deviceId - ds2.deviceId).collect(Collectors.toList());
    }
}
