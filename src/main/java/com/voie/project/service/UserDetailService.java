package com.voie.project.service;

import java.util.Collections;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.voie.project.models.Utilisateur;
import com.voie.project.repository.UtilisateurRepository;



@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UtilisateurRepository utilisateurRepo;

    public Utilisateur getCurrentAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return utilisateurRepo.findByNom(authentication.getName());
    }

    @Override
    public UserDetails loadUserByUsername(String nom) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepo.findByNom(nom);
        if (utilisateur == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(
        		utilisateur.getNom(),
        		utilisateur.getMot_de_passe(),
            Collections.singletonList(new SimpleGrantedAuthority(utilisateur.getProfile().getProfileName()))); // Assurez-vous de préfixer avec ROLE_
    }

    
    @Transactional
    public void updateUser(Utilisateur utilisateur) {
        Optional<Utilisateur> existingUser = utilisateurRepo.findById(utilisateur.getId());
        if (existingUser.isEmpty()) {
            throw new EntityNotFoundException("User not found with ID: " + utilisateur.getId());
        }

        Utilisateur entity = existingUser.get();
        BeanUtils.copyProperties(utilisateur, entity, "id");
        utilisateurRepo.save(entity);
    }

}