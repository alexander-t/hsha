package se.tarlinder.hsha.hub.event.ringbuffer;

public class SuperEvent {
    public int id;
    public EventType eventType;
    public String value;
    public String dataType;
    public String deviceName;

    public boolean isSensorEvent() {
        return EventType.SENSOR == eventType;
    }

    public boolean isDeviceEvent() {
        return EventType.DEVICE == eventType;
    }
}
