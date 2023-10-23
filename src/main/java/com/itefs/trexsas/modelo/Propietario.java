package com.itefs.trexsas.modelo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "propietario")
public class Propietario {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_propietario")
    private Long idPropietario;
	@Column(name = "estado_propietario")
	private Integer estadoPropietario;
	@Column(name = "fecha_registro_propietario")
	private String fechaRegistroPropietario;
	@Column(name = "fecha_actualizacion_propietario")
	private String fechaActualizacionPropietario;
	@OneToOne
    @JoinColumn(name = "registrado_por_propietario", referencedColumnName = "id_persona")
	private Persona registradoPorPropietario;
	@OneToOne
    @JoinColumn(name = "actualizado_por_propietario", referencedColumnName = "id_persona")
	private Persona actualizadoPorPropietario;
	@OneToOne
    @JoinColumn(name = "persona_id_persona", nullable = false, updatable = false)
    private Persona persona;
	
	public Propietario(){
		super();
	}

	public Long getIdPropietario() {
		return idPropietario;
	}

	public void setIdPropietario(Long idPropietario) {
		this.idPropietario = idPropietario;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Integer getEstadoPropietario() {
		return estadoPropietario;
	}

	public void setEstadoPropietario(Integer estadoPropietario) {
		this.estadoPropietario = estadoPropietario;
	}

	public String getFechaRegistroPropietario() {
		return fechaRegistroPropietario;
	}

	public void setFechaRegistroPropietario(String fechaRegistroPropietario) {
		this.fechaRegistroPropietario = fechaRegistroPropietario;
	}

	public String getFechaActualizacionPropietario() {
		return fechaActualizacionPropietario;
	}

	public void setFechaActualizacionPropietario(String fechaActualizacionPropietario) {
		this.fechaActualizacionPropietario = fechaActualizacionPropietario;
	}

	public Persona getRegistradoPorPropietario() {
		return registradoPorPropietario;
	}

	public void setRegistradoPorPropietario(Persona registradoPorPropietario) {
		this.registradoPorPropietario = registradoPorPropietario;
	}

	public Persona getActualizadoPorPropietario() {
		return actualizadoPorPropietario;
	}

	public void setActualizadoPorPropietario(Persona actualizadoPorPropietario) {
		this.actualizadoPorPropietario = actualizadoPorPropietario;
	}
	
	
}
