package com.mcc.outify.weathers.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "weathersources")
public class WeatherSourceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long weatherSourceId;

    @Column(nullable = false)
    private WeatherSource source;

    public WeatherSourceEntity(WeatherSource source) {
        this.source = source;
    }

    public enum WeatherSource {
        METEO, YR;
    }

}
