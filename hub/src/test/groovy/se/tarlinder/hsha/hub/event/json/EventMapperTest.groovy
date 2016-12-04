package se.tarlinder.hsha.hub.event.json

import se.tarlinder.hsha.hub.event.ringbuffer.EventMapper
import se.tarlinder.hsha.hub.event.ringbuffer.EventType
import se.tarlinder.hsha.hub.event.ringbuffer.SuperEvent
import spock.lang.Specification

public class EventMapperTest extends Specification {

    def "Construct super event from sensor event"() {
        given:
        SuperEvent superEvent = new SuperEvent()

        when:
        EventMapper.map(new SensorEvent([sensorId: 10, value: "22", dataType: "temp"]), superEvent)

        then:
        superEvent.id == 10
        superEvent.value == "22"
        superEvent.dataType == "temp"
        superEvent.eventType == EventType.SENSOR
        superEvent.deviceName == null
    }

    def "Construct super event from device event"() {
        given:
        SuperEvent superEvent = new SuperEvent()

        when:
        EventMapper.map(new DeviceEvent([deviceId: 20, value: "ON", deviceName: "switch"]), superEvent)

        then:
        superEvent.id == 20
        superEvent.value == "ON"
        superEvent.dataType == null
        superEvent.eventType == EventType.DEVICE
        superEvent.deviceName == "switch"
    }
}
