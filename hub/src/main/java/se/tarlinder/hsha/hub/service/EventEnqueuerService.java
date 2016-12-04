package se.tarlinder.hsha.hub.service;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.tarlinder.hsha.hub.disruptor.SuperEventFactory;
import se.tarlinder.hsha.hub.event.json.DeviceEvent;
import se.tarlinder.hsha.hub.event.json.SensorEvent;
import se.tarlinder.hsha.hub.event.ringbuffer.EventMapper;
import se.tarlinder.hsha.hub.event.ringbuffer.SuperEvent;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
public class EventEnqueuerService {

    private static final int DISRUPTOR_BUFFER_SIZE = 4096;

    private final Executor executor = Executors.newCachedThreadPool();
    private final Disruptor<SuperEvent> disruptor;
    private final RingBuffer<SuperEvent> ringBuffer;

    @Autowired
    public EventEnqueuerService(SuperEventList eventHandlers) {
        disruptor = new Disruptor<>(new SuperEventFactory(), DISRUPTOR_BUFFER_SIZE, executor);
        disruptor.handleEventsWith(eventHandlers.getHandlers().toArray(new EventHandler[0]));
        ringBuffer = disruptor.getRingBuffer();
        disruptor.start();
    }

    public void enqueue(SensorEvent sensorEvent) {
        long sequence = ringBuffer.next();
        try {
            SuperEvent bufferSensorEvent = ringBuffer.get(sequence);
            EventMapper.map(sensorEvent, bufferSensorEvent);
        } finally {
            ringBuffer.publish(sequence);
        }
    }

    public void enqueue(DeviceEvent deviceEvent) {
        long sequence = ringBuffer.next();
        try {
            SuperEvent bufferSensorEvent = ringBuffer.get(sequence);
            EventMapper.map(deviceEvent, bufferSensorEvent);
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
