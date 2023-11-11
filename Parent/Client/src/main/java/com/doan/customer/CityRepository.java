package com.doan.customer;

import com.doan.mutual.entity.City;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CityRepository extends CrudRepository<City, Integer> {
    public List<City> findAllByOrderByNameAsc();
}
