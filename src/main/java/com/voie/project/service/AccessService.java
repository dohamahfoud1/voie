package com.voie.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.voie.project.models.Access;
import com.voie.project.models.Permissions;
import com.voie.project.models.Profiles;
import com.voie.project.repository.AccessRepository;

@Service
public class AccessService {

	@Autowired
    private AccessRepository accessRepository;

    public void saveAccess(Profiles profile, Permissions permission) {
        Access access = new Access();
        access.setProfile(profile);
        access.setPermission(permission);
        accessRepository.save(access);
    }
    
    public void updateAccess(Profiles profile, List<Permissions> permissions) {
        // Supprime tous les accès existants pour ce profil
        accessRepository.deleteByProfileId(profile.getId());

        // Crée de nouveaux accès avec les permissions sélectionnées
        for (Permissions permission : permissions) {
            Access access = new Access();
            access.setProfile(profile);
            access.setPermission(permission);
            accessRepository.save(access);
        }
    }
}
