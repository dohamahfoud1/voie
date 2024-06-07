package com.voie.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.voie.project.models.SpecificPermissions;
import com.voie.project.repository.*;

@Service
public class SpecificPermissionsService {
	
	 @Autowired
	    private specificPermissionRepository specificPermissionRepository;

	    public List<SpecificPermissions> findByPermissionId(Long permId) {
	        return specificPermissionRepository.findByPermissionId(permId);
	    }

	    public List<SpecificPermissions> getAllOptions() {
	        return specificPermissionRepository.findAll();
	    }

	    public void saveOption(SpecificPermissions option) {
	        specificPermissionRepository.save(option);
	    }
}
