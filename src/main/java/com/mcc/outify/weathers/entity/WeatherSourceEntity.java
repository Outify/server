package com.mcc.outify.weathers.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "weathersource")
public class WeatherSourceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long weatherSourceId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WeatherSource source;

    public WeatherSourceEntity(WeatherSource source) {
        this.source = source;
    }

    public enum WeatherSource {
        METEO, YR;
    }

}
