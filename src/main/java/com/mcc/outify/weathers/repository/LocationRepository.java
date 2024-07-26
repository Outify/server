package com.mcc.outify.weathers.repository;

import com.mcc.outify.weathers.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, Long> {

    Optional<LocationEntity> findByLongitudeAndLatitude(Double longitude, Double latitude);

    LocationEntity findByHighAddrAndMidAddrAndLowAddr(String highAddr, String midAddr, String lowAddr);

}
