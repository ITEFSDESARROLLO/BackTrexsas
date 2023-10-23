package com.itefs.trexsas.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@Table(name="inscripcionescapacitacion")
public class InscripcionCapacitacion 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_inscripciones_capacitacion")
	private Long id;
	
	@NotNull
	@Column(name="id_capacitacion")
	private Long capacitacion;
	
	@NotNull
	@Column(name="id_conductor")
	private Long persona;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCapacitacion() {
		return capacitacion;
	}

	public void setCapacitacion(Long capacitacion) {
		this.capacitacion = capacitacion;
	}

	public Long getPersona() {
		return persona;
	}

	public void setPersona(Long persona) {
		this.persona = persona;
	}	

}
