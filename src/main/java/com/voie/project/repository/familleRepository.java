package com.voie.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.voie.project.models.Famille;

public interface familleRepository extends JpaRepository<Famille, Long> {

}
