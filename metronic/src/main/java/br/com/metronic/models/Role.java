package br.com.metronic.models;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Entity
public @Data class Role implements GrantedAuthority {

	/** Serial Version */
	private static final long serialVersionUID = 4591392921342043304L;
	
	@Id
	@NotBlank
	private String name;
	
	@Override
	public String getAuthority() {
		return name;
	}

}
