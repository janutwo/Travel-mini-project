package com.people.travel.user.repository;

import com.people.travel.core.repository.TravelRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface UserTravelRepository extends TravelRepository {

    @Query("select t.startDate from Travel t where t.disabled= false and(current_date < t.startDate) order by t.startDate desc")
    List<LocalDate> findAllByCurrentDateAfter(Pageable pageable);

    @Query("select t.startDate from Travel t where t.disabled= false and(t.startDate < current_date) order by t.startDate desc")
    List<LocalDate> findRecentlyTravel(Pageable pageable);
}
