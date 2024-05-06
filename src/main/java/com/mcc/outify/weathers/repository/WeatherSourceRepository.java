package com.mcc.outify.weathers.repository;

import com.mcc.outify.weathers.entity.WeatherSourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherSourceRepository extends JpaRepository<WeatherSourceEntity, Long> {

    WeatherSourceEntity findBySource(WeatherSourceEntity.WeatherSource source);

}
