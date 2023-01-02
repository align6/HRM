package com.ihrm.system.dao;

import com.ihrm.domain.system.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

@Component
public interface CityDao extends JpaRepository<City,String>, JpaSpecificationExecutor<City> {
}
