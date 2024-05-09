package com.mcc.outify.weathers.repository;

import com.mcc.outify.weathers.entity.LocationEntity;
import com.mcc.outify.weathers.entity.WeatherDataEntity;
import com.mcc.outify.weathers.entity.WeatherSourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherDataEntity, Long> {

    void deleteByLocationAndWeatherSource(LocationEntity location, WeatherSourceEntity weatherSource);

    List<WeatherDataEntity> findAllByLocationAndWeatherSource(LocationEntity location, WeatherSourceEntity weatherSource);

}
