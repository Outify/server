package com.mcc.outify.weathers;

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

    enum WeatherSource {
        METEO, YR
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long weatherSourceId;

    @Column(nullable = false)
    private WeatherSource source;

}
