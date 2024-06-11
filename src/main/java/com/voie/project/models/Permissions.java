package com.voie.project.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;



@Entity
@Table(name="permissions")
public class Permissions {

	
	  @Id  	
	    @GeneratedValue(strategy=GenerationType.IDENTITY)
		private Long id;
	    private String permission;
		
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getPermission() {
			return permission;
		}
		public void setPermission(String permission) {
			this.permission = permission;
		}
		public Permissions(Long id, String permission) {
			super();
			this.id = id;
			this.permission = permission;
		}
		@Override
		public String toString() {
			return "Permissions [id=" + id + ", permission=" + permission + "]";
		}
		
		public Permissions() {
		}
		
		 @OneToMany(mappedBy = "permission")
		    private List<SpecificPermissions> specificPermissions;

		public List<SpecificPermissions> getSpecificPermissions() {
			return specificPermissions;
		}
		public void setSpecificPermissions(List<SpecificPermissions> specificPermissions) {
			this.specificPermissions = specificPermissions;
		}
	

}
