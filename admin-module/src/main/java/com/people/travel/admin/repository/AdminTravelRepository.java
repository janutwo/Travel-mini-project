package com.people.travel.admin.repository;

import com.people.travel.core.entity.Travel;
import com.people.travel.core.repository.TravelRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdminTravelRepository extends TravelRepository {

    @Query("select t from Travel t left join fetch t.accommodations  where t.id=:id and t.disabled= false")
    Optional<Travel> findTravelByIdWithAccommodations(@Param("id")Long id);
}
