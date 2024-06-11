package com.voie.project.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.voie.project.models.Utilisateur;
import com.voie.project.repository.UtilisateurRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	 @Autowired
	    private UtilisateurRepository utilisateurRepository;

	    @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        Utilisateur utilisateur = utilisateurRepository.findByNom(username);
	        if (utilisateur == null) {
	            throw new UsernameNotFoundException("Utilisateur non trouvé");
	        }
	        return org.springframework.security.core.userdetails.User.withUsername(utilisateur.getNom())
	                .password(utilisateur.getMot_de_passe())
	                .authorities(utilisateur.getProfile().getProfileName()) // Utilisation du profile comme autorité
	                .build();
	    }
}