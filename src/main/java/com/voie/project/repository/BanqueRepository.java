package com.voie.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.voie.project.models.Banque;

public interface BanqueRepository extends JpaRepository<Banque, Long> {

}
