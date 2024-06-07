package com.voie.project.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.voie.project.models.Access;
import com.voie.project.models.Permissions;
import com.voie.project.models.Profiles;


public interface AccessRepository extends JpaRepository<Access, Long> {
	 void deleteByProfileId(Long id);
	 List<Access> findByProfileId(Long profileId);
	List<Object[]> findAccessWithPermissionNameByProfileId(Long profileId);
	List<Permissions> findAccessWithPermissionIdByProfileId(Long profileId);
	List<Access> findByProfile(Profiles profile);
	
	
}
