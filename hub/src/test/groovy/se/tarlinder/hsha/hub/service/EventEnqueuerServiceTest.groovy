package se.tarlinder.hsha.hub.service

import se.tarlinder.hsha.hub.event.json.DeviceEvent
import se.tarlinder.hsha.hub.event.json.SensorEvent
import spock.lang.Specification

import java.util.concurrent.TimeUnit

import static org.awaitility.Awaitility.await

class EventEnqueuerServiceTest extends Specification {

    def eventHandlerSpy
    def testedService

    def setup() {
        eventHandlerSpy = new SuperEventHandlerSpy()
        def handlers = new DefaultSuperEventList()
        handlers.addEventHandler(eventHandlerSpy)
        testedService = new EventEnqueuerService(handlers)
    }

    def "An enqueued sensor event is consumed"() {

        given:
        def enquedEvent = new SensorEvent([sensorId: 123, value: 10, dataType: 'temp'])

        when:
        testedService.enqueue(enquedEvent)

        then:
        await().atMost(1, TimeUnit.SECONDS).until({ eventHandlerSpy.recordedEvents.size() == 1 })
        def consumedEvent = eventHandlerSpy.recordedEvents.get(0)
        consumedEvent.id == 123
        consumedEvent.dataType == 'temp'
    }

    def "An enqueued device event is consumed"() {

        given:
        def enquedEvent = new DeviceEvent([deviceId: 321, value: 'ON', deviceName: 'motion sensor'])

        when:
        testedService.enqueue(enquedEvent)

        then:
        await().atMost(1, TimeUnit.SECONDS).until({ eventHandlerSpy.recordedEvents.size() == 1 })
        def consumedEvent = eventHandlerSpy.recordedEvents.get(0)
        consumedEvent.id == 321
        consumedEvent.deviceName == 'motion sensor'
    }

    def "Multiple mixed events are consumed"() {

        when:
        testedService.enqueue(new DeviceEvent([deviceId: 321, value: 'ON', deviceName: 'motion sensor']))
        testedService.enqueue(new SensorEvent([sensorId: 123, value: 10, dataType: 'temp']))
        testedService.enqueue(new SensorEvent([sensorId: 124, value: 20, dataType: 'humidity']))
        testedService.enqueue(new DeviceEvent([deviceId: 321, value: 'OFF', deviceName: 'motion sensor']))

        then:
        await().atMost(1, TimeUnit.SECONDS).until({ eventHandlerSpy.recordedEvents.size() == 4 })

        eventHandlerSpy.recordedEvents.collect {it.id} == [321, 123, 124, 321]
    }

}
