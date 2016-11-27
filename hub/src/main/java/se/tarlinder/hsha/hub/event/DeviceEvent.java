package se.tarlinder.hsha.hub.event;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceEvent {

    @JsonProperty("device_id")
    public int deviceId;
    public String value;
    public String deviceName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeviceEvent that = (DeviceEvent) o;

        if (deviceId != that.deviceId) return false;
        return !(value != null ? !value.equals(that.value) : that.value != null) && !(deviceName != null ? !deviceName.equals(that.deviceName) : that.deviceName != null);

    }

    @Override
    public int hashCode() {
        int result = deviceId;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (deviceName != null ? deviceName.hashCode() : 0);
        return result;
    }
}
