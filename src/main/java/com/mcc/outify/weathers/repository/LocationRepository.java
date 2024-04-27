package com.mcc.outify.weathers.repository;

import com.mcc.outify.weathers.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {
    Optional<LocationEntity> findByName(String name);

    LocationEntity findByHighAddrAndMidAddrAndLowAddr(String highAddr, String midAddr, String lowAddr);
}
