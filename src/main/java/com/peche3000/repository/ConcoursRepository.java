package com.peche3000.repository;

import com.peche3000.entity.Concours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ConcoursRepository extends JpaRepository<Concours, Long> {

    @Query("SELECT COUNT(c) FROM Concours c JOIN c.participants p")
    long countInscriptions();
}
