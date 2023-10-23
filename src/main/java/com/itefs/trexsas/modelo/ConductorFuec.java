package com.itefs.trexsas.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="conductor_fuec")
public class ConductorFuec 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_conductor_fuec")
	private Long id;
	
	@NotNull
	@Column(name="conductor_id_conductor")
	private Integer conductor;
	
	@NotNull
	@Column(name="fuec_id_fuec")
	private Long fuec;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getConductor() {
		return conductor;
	}

	public void setConductor(Integer conductor) {
		this.conductor = conductor;
	}

	public Long getFuec() {
		return fuec;
	}

	public void setFuec(Long fuec) {
		this.fuec = fuec;
	}
	
	

}
