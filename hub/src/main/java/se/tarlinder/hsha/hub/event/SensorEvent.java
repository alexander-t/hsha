package se.tarlinder.hsha.hub.event;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SensorEvent {

    @JsonProperty("sensor_id")
    public int sensorId;
    public String value;

    @JsonProperty("data_type")
    public String dataType;

    @Override
    public String toString() {
        return "SensorEvent{" +
                "sensorId=" + sensorId +
                ", value='" + value + '\'' +
                ", dataType='" + dataType + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SensorEvent that = (SensorEvent) o;

        if (sensorId != that.sensorId) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;
        return !(dataType != null ? !dataType.equals(that.dataType) : that.dataType != null);

    }

    @Override
    public int hashCode() {
        int result = sensorId;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (dataType != null ? dataType.hashCode() : 0);
        return result;
    }
}
