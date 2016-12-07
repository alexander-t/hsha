package se.tarlinder.hsha.hub.disruptor.eventhandler

import se.tarlinder.hsha.hub.event.ringbuffer.EventType
import se.tarlinder.hsha.hub.event.ringbuffer.SuperEvent
import spock.lang.Specification

class DeviceValueRepositoryTest extends Specification {

    def testedRepository;

    def setup() {
        testedRepository = new DeviceValueRepository()
    }

    def "getLastValueFor: querying an unregistered device gives nothing"() {
        expect:
        testedRepository.getLastValueFor(1).isPresent() == false
    }

    def "If a device has ever been added to the repository, it appears in the summary"() {

        given:
        final boolean ringBufferEndOfBatch = true;
        testedRepository.onEvent(new SuperEvent([id: 4, eventType: EventType.DEVICE, deviceName: "Motion Sensor #1", value: "on"]), 1, ringBufferEndOfBatch)

        expect:
        testedRepository.getAll() == [new DeviceStatus(4, "Motion Sensor #1", "on")]
    }

    def "Device list is sorted on device id"() {
        given:
        def device4Event = new SuperEvent([id: 4, eventType: EventType.DEVICE, deviceName: "Motion Sensor #1", value: "on"])
        def device44Event = new SuperEvent([id: 44, eventType: EventType.DEVICE, deviceName: "Magnetic Sensor", value: "off"])
        def device888Event = new SuperEvent([id: 888, eventType: EventType.DEVICE, deviceName: "Outdoor Motion Sensor #1", value: "on"])

        when:
        final boolean ringBufferEndOfBatch = true;
        testedRepository.onEvent(device44Event, 1, ringBufferEndOfBatch)
        testedRepository.onEvent(device888Event, 2, ringBufferEndOfBatch)
        testedRepository.onEvent(device4Event, 3, ringBufferEndOfBatch)

        then:
        testedRepository.getAll() == [new DeviceStatus(4, "Motion Sensor #1", "on"),
                                      new DeviceStatus(44, "Magnetic Sensor", "off"),
                                      new DeviceStatus(888, "Outdoor Motion Sensor #1", "on")]
    }
}
