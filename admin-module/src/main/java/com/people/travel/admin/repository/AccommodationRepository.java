package com.people.travel.admin.repository;

import com.people.travel.core.entity.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

    @Query("select acc from Accommodation acc join fetch acc.travel where acc.travel.id = :id and acc.travel.disabled = false")
    List<Accommodation> findAccommodationWithTravel(@Param("id") Long id);
}
