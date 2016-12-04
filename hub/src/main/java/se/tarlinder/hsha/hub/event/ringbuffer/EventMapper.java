package se.tarlinder.hsha.hub.event.ringbuffer;

import se.tarlinder.hsha.hub.event.json.DeviceEvent;
import se.tarlinder.hsha.hub.event.json.SensorEvent;

/**
 * Maps events from JSON events to super events
 */
public class EventMapper {
    public static void map(SensorEvent sensorEvent, SuperEvent superEvent) {
        superEvent.id = sensorEvent.sensorId;
        superEvent.eventType = EventType.SENSOR;
        superEvent.value = sensorEvent.value;
        superEvent.dataType = sensorEvent.dataType;
        superEvent.deviceName = null;
    }

    public static void map(DeviceEvent sensorEvent, SuperEvent superEvent) {
        superEvent.id = sensorEvent.deviceId;
        superEvent.eventType = EventType.DEVICE;
        superEvent.value = sensorEvent.value;
        superEvent.deviceName = sensorEvent.deviceName;
        superEvent.dataType = null;
    }
}
