package com.voie.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.voie.project.models.Permissions;
import com.voie.project.repository.PermissionsRepository;

@Service
public class PermissionsService {

	 @Autowired
	    private PermissionsRepository permissionRepository;

	    public List<Permissions> findAll() {
	        return permissionRepository.findAll();
	    }

	    public Permissions findById(Long id) {
	        return permissionRepository.findById(id).orElse(null);
	    }

	    public Permissions save(Permissions permission) {
	        return permissionRepository.save(permission);
	    }

	    public void deleteById(Long id) {
	        permissionRepository.deleteById(id);
	    }
}
