package com.people.travel.admin.repository;

import com.people.travel.core.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TravelRepository extends JpaRepository<Travel, Long> {
}
