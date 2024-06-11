package com.voie.project.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.voie.project.models.Profiles;

public interface ProfileRepository  extends JpaRepository<Profiles, Long> {
	Optional<Profiles> findById(Long id);
	public Profiles findByProfileName(String profileName); 
	List<Profiles> findByProfileNameContainingIgnoreCase(String profileName);
	
	 @Query("SELECT p FROM Profiles p LEFT JOIN FETCH p.accesses a LEFT JOIN FETCH a.permission WHERE p.id = :id")
	    Profiles findByIdWithPermissions(@Param("id") Long id);
	


}
