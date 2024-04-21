package com.mcc.outify.weathers;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "locations")
public class LocationEntity {

    enum Category {
        DISTRICT, MOUNTAIN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationId;

    @Column(nullable = false)
    private static Category category;

    @Column(nullable = false)
    private String longitude;

    @Column(nullable = false)
    private String latitude;

    @Column(nullable = false)
    private String highAdress;

    @Column(nullable = false)
    private String midAdress;

    @Column(nullable = false)
    private String lowAdress;

    public LocationEntity(String highAddr, String midAddr, String lowAddr, Double longitude, Double latitude) {
        this.highAdress = highAddr;
        this.midAdress = midAddr;
        this.lowAdress = lowAddr;
        this.longitude = String.valueOf(longitude);
        this.latitude = String.valueOf(latitude);
    }

}
