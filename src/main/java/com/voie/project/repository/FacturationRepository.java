package com.voie.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.voie.project.models.Facturation;

public interface FacturationRepository  extends JpaRepository<Facturation, Long>  {

}
