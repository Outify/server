package com.mcc.outify.weathers.entity;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationId;

    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private String name;
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

    public LocationEntity(String category, String name, String highAddr, String midAddr, String lowAddr, Double longitude, Double latitude) {
        this.category = Category.fromString(category);
        this.name = name;
        this.highAdress = highAddr;
        this.midAdress = midAddr;
        this.lowAdress = lowAddr;
        this.longitude = String.valueOf(longitude);
        this.latitude = String.valueOf(latitude);
    }

    enum Category {
        DISTRICT, MOUNTAIN;

        private static Category fromString(String inputStr) {
            if (inputStr != null) {
                for (Category category : Category.values()) {
                    if (inputStr.equalsIgnoreCase(category.name())) {
                        return category;
                    }
                }
            }
            throw new IllegalArgumentException("Invalid category: " + inputStr);
        }
    }

}
