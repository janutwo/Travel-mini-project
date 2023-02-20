package com.people.travel.admin.repository;

import com.people.travel.core.entity.TravelDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelDocumentRepository extends JpaRepository<TravelDocument, Long> {
}
