package se.tarlinder.hsha.hub.disruptor.eventhandler

import se.tarlinder.hsha.hub.event.SensorEvent
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

    def "onEvent: events are mapped to sensor ids and their values are grouped by data type"() {
        given:
        def event = new SensorEvent()
        event.sensorId = 100
        event.dataType = "temp"
        event.value = "10.5"

        when:
        testedRepository.onEvent(event, 1, true)

        then:
        testedRepository.getLastValueFor(100).get().get("temp") == "10.5"
    }
}
