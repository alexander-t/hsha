package se.tarlinder.hsha.hub.disruptor.eventhandler

import se.tarlinder.hsha.hub.event.json.SensorEvent
import se.tarlinder.hsha.hub.event.ringbuffer.EventType
import se.tarlinder.hsha.hub.event.ringbuffer.SuperEvent
import spock.lang.Specification

class SensorValueRepositoryTest extends Specification {

    def testedRepository;

    def setup() {
        testedRepository = new SensorValueRepository()
    }

    def "getLastValueFor: querying an unregistered sensor gives nothing"() {
        expect:
        testedRepository.getLastValueFor(1).isPresent() == false
    }

    def "Events are mapped to sensor ids and their values are grouped by data type"() {
        final int sensorId = 100
        given:
        def tempEvent = new SuperEvent([id: sensorId, eventType: EventType.SENSOR, dataType: "temp", value: "10.5"])
        def humidityEvent = new SuperEvent([id: sensorId, eventType: EventType.SENSOR, dataType: "humidity", value: "33"])

        when:
        final boolean ringBufferEndOfBatch = true;
        testedRepository.onEvent(tempEvent, 1, ringBufferEndOfBatch)
        testedRepository.onEvent(humidityEvent, 2, ringBufferEndOfBatch)

        then:
        testedRepository.getLastValueFor(sensorId).get().get("temp") == "10.5"
        testedRepository.getLastValueFor(sensorId).get().get("humidity") == "33"
    }
}
