package com.voie.project.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.voie.project.models.Profiles;

public interface ProfileRepository  extends JpaRepository<Profiles, Long> {
	Optional<Profiles> findById(Long id);
	public Profiles findByProfileName(String profileName); 
	List<Profiles> findByProfileNameContainingIgnoreCase(String profileName);

}
