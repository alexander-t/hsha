package se.tarlinder.hsha.hub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.tarlinder.hsha.hub.disruptor.eventhandler.DeviceValueRepository;
import se.tarlinder.hsha.hub.disruptor.eventhandler.SensorValueRepository;
import se.tarlinder.hsha.hub.disruptor.eventhandler.StdoutPrintingSuperEventHandler;

import javax.annotation.PostConstruct;

@Component
public class DefaultEventHandlers extends DefaultSuperEventList {

    @Autowired
    private SensorValueRepository sensorValueRepository;

    @Autowired
    private DeviceValueRepository deviceValueRepository;


    @PostConstruct
    public void init() {
        addEventHandler(new StdoutPrintingSuperEventHandler());
        addEventHandler(sensorValueRepository);
        addEventHandler(deviceValueRepository);
    }
}
