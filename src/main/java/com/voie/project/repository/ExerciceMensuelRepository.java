package com.voie.project.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.voie.project.models.ExerciceMensuel;

public interface ExerciceMensuelRepository extends JpaRepository<ExerciceMensuel, Long> {
	  ExerciceMensuel findByMois(LocalDate mois);
	    List<ExerciceMensuel> findByArchiveTrue();
}