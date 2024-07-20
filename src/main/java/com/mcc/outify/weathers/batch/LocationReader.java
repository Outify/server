package com.mcc.outify.weathers.batch;

import com.mcc.outify.weathers.entity.LocationEntity;
import com.mcc.outify.weathers.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@RequiredArgsConstructor
@Component
public class LocationReader implements ItemReader<LocationEntity> {

    private final LocationRepository locationRepository;

    private Iterator<LocationEntity> locationIterator;

    @Override
    public LocationEntity read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (locationIterator == null) {
            locationIterator = locationRepository.findAll().iterator();
        }
        if (locationIterator.hasNext()) {
            return locationIterator.next();
        } else {
            return null;
        }
    }
}
