package com.voie.project.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Access {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @ManyToOne
	    @JoinColumn(name = "profile_id")
	    private Profiles profile;

	    @ManyToOne
	    @JoinColumn(name = "permission_id")
	    private Permissions permission;


	    @ManyToOne
	    @JoinColumn(name = "specific_paermission_id")
	    private SpecificPermissions speceficPermissions;


		public Long getId() {
			return id;
		}


		public void setId(Long id) {
			this.id = id;
		}


		public Profiles getProfile() {
			return profile;
		}


		public void setProfile(Profiles profile) {
			this.profile = profile;
		}


		public Permissions getPermission() {
			return permission;
		}


		public void setPermission(Permissions permission) {
			this.permission = permission;
		}


		public SpecificPermissions getSpeceficPermissions() {
			return speceficPermissions;
		}


		public void setSpeceficPermissions(SpecificPermissions speceficPermissions) {
			this.speceficPermissions = speceficPermissions;
		}


		public Access(Long id, Profiles profile, Permissions permission, SpecificPermissions speceficPermissions) {
			super();
			this.id = id;
			this.profile = profile;
			this.permission = permission;
			this.speceficPermissions = speceficPermissions;
		}


		public Access() {
			
		}
	    
}
