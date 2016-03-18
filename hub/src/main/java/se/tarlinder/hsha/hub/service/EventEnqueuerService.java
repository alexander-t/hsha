package se.tarlinder.hsha.hub.service;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.tarlinder.hsha.hub.disruptor.SensorEventFactory;
import se.tarlinder.hsha.hub.disruptor.eventhandler.SensorValueRepository;
import se.tarlinder.hsha.hub.disruptor.eventhandler.StdoutPrintingSensorEventHandler;
import se.tarlinder.hsha.hub.event.SensorEvent;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Component
public class EventEnqueuerService {

    private static final int DISRUPTOR_BUFFER_SIZE = 4096;

    private final Executor executor = Executors.newCachedThreadPool();
    private final Disruptor<SensorEvent> disruptor;
    private final RingBuffer<SensorEvent> ringBuffer;

    @Autowired
    private SensorValueRepository sensorValueRepository;

    public EventEnqueuerService() {
        disruptor = new Disruptor<>(new SensorEventFactory(), DISRUPTOR_BUFFER_SIZE, executor);
        ringBuffer = disruptor.getRingBuffer();
    }

    // The Spring context must be set up and the SensorValueRepository bean available
    // before handing it over to the disruptor.
    @PostConstruct
    public void initialize() {
        disruptor.handleEventsWith(
                new StdoutPrintingSensorEventHandler(),
                sensorValueRepository);
        disruptor.start();
    }

    public void enqueue(SensorEvent sensorEvent) {
        long sequence = ringBuffer.next();
        try {
            SensorEvent bufferSensorEvent = ringBuffer.get(sequence);
            bufferSensorEvent.sensorId = sensorEvent.sensorId;
            bufferSensorEvent.dataType = sensorEvent.dataType;
            bufferSensorEvent.value = sensorEvent.value;
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
