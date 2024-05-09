package com.mcc.outify.weathers.repository;

import com.mcc.outify.weathers.entity.WeatherSourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherSourceRepository extends JpaRepository<WeatherSourceEntity, Long> {

    WeatherSourceEntity findBySource(WeatherSourceEntity.WeatherSource source);

}
