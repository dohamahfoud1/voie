package com.voie.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.voie.project.models.Permissions;
import com.voie.project.models.Profiles;
import com.voie.project.repository.PermissionsRepository;
import com.voie.project.repository.ProfileRepository;

@Service
public class ProfileService {

	
	  @Autowired
	    private ProfileRepository profileRepository;

	    @Autowired
	    private PermissionsRepository permissionRepository;

	    @Autowired
	    private AccessService accessService;

	    public void saveProfileWithPermissions(Profiles profile, List<Long> permissionIds) {
	    	
	        profile = profileRepository.save(profile);  // Save the profile and get the generated ID

	        List<Permissions> permissions = permissionRepository.findByIdIn(permissionIds);

	        for (Permissions permission : permissions) {
	            accessService.saveAccess(profile, permission);
	        }
	    }
	    
	    public void updateProfile(Profiles profile, List<Long> permissionIds) {
	    	 profile = profileRepository.save(profile);
	        // Récupérer la liste des permissions sélectionnées
	        List<Permissions> permissions = permissionRepository.findByIdIn(permissionIds);
	        for (Permissions permission : permissions) {
	            accessService.updateAccess(profile, permissions);
	        }
	        
	    }
}
