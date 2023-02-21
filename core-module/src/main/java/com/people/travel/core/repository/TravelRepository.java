package com.people.travel.core.repository;

import com.people.travel.core.entity.Travel;
import com.people.travel.core.entity.TravelStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public interface TravelRepository extends JpaRepository<Travel, Long> {

    @Query("select t from Travel t where t.status=:status and t.disabled=false order by t.modifiedDate desc")
    Page<Travel> findAllTravel(@Param("status") TravelStatus travelStatus, Pageable pageable);

    @Query("select t from Travel t where t.disabled = false and (t.modifiedDate between :startDate and :endDate) order by t.modifiedDate desc")
    List<Travel> findAllByModifiedDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate")LocalDateTime endDate);

}
