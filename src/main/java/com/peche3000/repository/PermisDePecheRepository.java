package com.peche3000.repository;

import com.peche3000.entity.PermisDePeche;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PermisDePecheRepository extends JpaRepository<PermisDePeche, Long> {

    @Query("SELECT p FROM PermisDePeche p WHERE p.client.email = :email")
    List<PermisDePeche> findByClientEmail(@Param("email") String email);
}
