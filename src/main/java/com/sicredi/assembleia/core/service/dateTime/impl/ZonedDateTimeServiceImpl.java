package com.sicredi.assembleia.core.service.dateTime.impl;

import com.sicredi.assembleia.core.factory.DateTimeFactory;
import com.sicredi.assembleia.core.service.dateTime.ZonedDateTimeService;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class ZonedDateTimeServiceImpl implements ZonedDateTimeService {
    @Override
    public ZonedDateTime now() {
        return DateTimeFactory.now();
    }
}
