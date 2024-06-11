package com.voie.project.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.voie.project.models.Utilisateur;

public class UserDetail implements UserDetails {

	private static final long serialVersionUID = 1L;
	//private static final long serialVersionUID = 1L;
	private Utilisateur utilisateur;
	
	public UserDetail(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return utilisateur.getMot_de_passe();
	}

	@Override
	public String getUsername() {
		return utilisateur.getNom();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public String getFullName() {
		return utilisateur.getNom() + " " + utilisateur.getNom();
	}
	 public Long getUserId() {	        
	    return utilisateur.getId();
	  }
}