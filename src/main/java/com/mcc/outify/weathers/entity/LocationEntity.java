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
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String longitude;

    @Column(nullable = false)
    private String latitude;

    @Column(nullable = false)
    private String highAddr;

    @Column(nullable = false)
    private String midAddr;

    @Column(nullable = false)
    private String lowAddr;

    public LocationEntity(String category, String name, String highAddr, String midAddr, String lowAddr, Double longitude, Double latitude) {
        this.category = Category.fromString(category);
        this.name = name;
        this.highAddr = highAddr;
        this.midAddr = midAddr;
        this.lowAddr = lowAddr;
        this.longitude = String.valueOf(longitude);
        this.latitude = String.valueOf(latitude);
    }

    enum Category {
        DISTRICT, MOUNTAIN;

        private static Category fromString(String inputStr) {
            if (inputStr != null) {
                for (Category category : Category.values()) {
                    if (category.name().equalsIgnoreCase(inputStr)) {
                        return category;
                    }
                }
            }
            throw new IllegalArgumentException("Invalid category: " + inputStr);
        }
    }

}
