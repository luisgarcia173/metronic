package br.com.metronic.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Entity
public @Data class Role implements GrantedAuthority {

	/** Serial Version */
	private static final long serialVersionUID = 4591392921342043304L;
	
	public Role() {}
	public Role(String name) {
		this.name = name;
	}
	
	@Id
	@NotBlank
	private String name;
	
	@Transient
	private String shortName;
	
	@Override
	public String getAuthority() {
		return name;
	}

	public String getShortName(){
		return name.substring(name.indexOf("_")+1, name.length()).toLowerCase();
	}
	
}
