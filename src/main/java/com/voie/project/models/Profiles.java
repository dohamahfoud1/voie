package com.voie.project.models;



import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name="profiles")
public class Profiles {
	
	    @Id  	
	    @GeneratedValue(strategy=GenerationType.IDENTITY)
		private Long id;
	    @Column(name = "profile_name")
	    private String profileName;

	    @OneToMany(mappedBy = "profile")
	    private Set<Access> accesses;
	 
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getProfileName() {
			return profileName;
		}
		public void setProfileName(String profileName) {
			this.profileName = profileName;
		}
		
		

	    public Set<Access> getAccesses() {
			return accesses;
		}
		public void setAccesses(Set<Access> accesses) {
			this.accesses = accesses;
		}
		public Set<Permissions> getPermissions() {
	        return accesses.stream()
	                .map(Access::getPermission)
	                .collect(Collectors.toSet());
	    }
		
		
		

}
