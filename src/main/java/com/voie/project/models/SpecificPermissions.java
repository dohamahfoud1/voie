package com.voie.project.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class SpecificPermissions {

	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	 private String name;
	    @ManyToOne
	    @JoinColumn(name = "permission_id")
	    private Permissions permission;
        
	    
	    

		public SpecificPermissions() {
			super();
		}


		public Long getId() {
			return id;
		}


		public void setId(Long id) {
			this.id = id;
		}


		public String getName() {
			return name;
		}


		public void setName(String name) {
			this.name = name;
		}


		public Permissions getPermission() {
			return permission;
		}


		public void setPermission(Permissions permission) {
			this.permission = permission;
		}


	    
	    
	    
}
