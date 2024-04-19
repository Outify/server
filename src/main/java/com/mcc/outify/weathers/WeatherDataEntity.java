package com.mcc.outify.weathers;

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
    private WeatherSourceEntity weatherSourceId;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column
    private String sky;

    @Column
    private String tmp;

    @Column
    private String pcp;

    @Column
    private String wsd;

    @Column
    private String wgu;

    @Column
    private String hum;

    @Column
    private String dpt;

}
