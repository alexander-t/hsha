package se.tarlinder.hsha.hub.disruptor.eventhandler;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class DeviceStatus {
    @JsonProperty("device_id")
    public final int deviceId;

    @JsonProperty("device_name")
    public final String deviceName;

    @JsonProperty("last_value")
    public final String lastValue;

   @JsonProperty("updated")
   public final long updated;

    public DeviceStatus(int deviceId, String deviceName, String lastValue, long updated) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.lastValue = lastValue;
        this.updated = updated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceStatus that = (DeviceStatus) o;
        return deviceId == that.deviceId &&
                updated == that.updated &&
                Objects.equals(deviceName, that.deviceName) &&
                Objects.equals(lastValue, that.lastValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deviceId, deviceName, lastValue, updated);
    }

    @Override
    public String toString() {
        return "DeviceStatus{" +
                "deviceId=" + deviceId +
                ", deviceName='" + deviceName + '\'' +
                ", lastValue='" + lastValue + '\'' +
                ", updated=" + updated +
                '}';
    }
}
