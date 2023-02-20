package com.people.travel.admin.repository;

import com.people.travel.core.entity.Travel;
import com.people.travel.core.entity.TravelStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface TravelRepository extends JpaRepository<Travel, Long> {

    @Query("select t from Travel t where t.status=:status and t.disabled=false order by t.modifiedDate desc")
    Page<Travel> findAllTravel(@Param("status") TravelStatus travelStatus, Pageable pageable);

    @Query("select t from Travel t left join fetch t.accommodations  where t.id=:id and t.disabled= false")
    Optional<Travel> findTravelByIdWithAccommodations(@Param("id")Long id);
}
