package com.voie.project.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;


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
