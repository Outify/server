package com.mcc.outify.weathers.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "weatherdatas")
public class WeatherDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long weatherDataId;

    @ManyToOne
    @JoinColumn(name = "locationId")
    private LocationEntity locaion;

    @ManyToOne
    @JoinColumn(name = "weatherSourceId")
    private WeatherSourceEntity weatherSource;

    @Column(nullable = false)
    private LocalDateTime time;

    @Column
    private String sky;

    @Column
    private Double tmp;

    @Column
    private Double pcp;

    @Column
    private Double wsd;

    @Column
    private Double wgu;

    @Column
    private Double hum;

    @Column
    private Double dpt;

    public WeatherDataEntity(LocationEntity locaion, WeatherSourceEntity weatherSource, LocalDateTime time, String sky, Double tmp, Double pcp, Double wsd, Double wgu, Double hum, Double dpt) {
        this.locaion = locaion;
        this.weatherSource = weatherSource;
        this.time = time;
        this.sky = sky;
        this.tmp = tmp;
        this.pcp = pcp;
        this.wsd = wsd;
        this.wgu = wgu;
        this.hum = hum;
        this.dpt = dpt;
    }

}
