package com.itefs.trexsas.modelo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "caja_compensacion")
public class CajaCompensacion {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_caja_compensacion")
    private Integer idCajaCompensacion;
	
	@Size(max = 50, message = "error, campo cajaCompensacion maximo de 50 digitos")
    @Column(name = "caja_compensacion")
    private String cajaCompensacion;
	
    @Column(name = "estado_caja_compensacion")
    private Integer estadoCajaCompensacion;
    @Column(name = "fecha_registro_caja_compensacion")
	private String fechaRegistroCajaCompensacion;
	@Column(name = "fecha_actualizacion_caja_compensacion")
	private String fechaActualizacionCajaCompensacion;
	@ManyToOne
    @JoinColumn(name = "registrado_por_caja_compensacion", referencedColumnName = "id_persona")
	private PersonaAutor registradoPorCajaCompensacion;
	@ManyToOne
    @JoinColumn(name = "actualizado_por_caja_compensacion", referencedColumnName = "id_persona")
	private PersonaAutor actualizadoPorCajaCompensacion;
    
    
	public CajaCompensacion() {
		super();
	}
	public Integer getIdCajaCompensacion() {
		return idCajaCompensacion;
	}
	public void setIdCajaCompensacion(Integer idCajaCompensacion) {
		this.idCajaCompensacion = idCajaCompensacion;
	}
	public String getCajaCompensacion() {
		return cajaCompensacion;
	}
	public void setCajaCompensacion(String cajaCompensacion) {
		this.cajaCompensacion = cajaCompensacion;
	}
	public Integer getEstadoCajaCompensacion() {
		return estadoCajaCompensacion;
	}
	public void setEstadoCajaCompensacion(Integer estadoCajaCompensacion) {
		this.estadoCajaCompensacion = estadoCajaCompensacion;
	}
	public String getFechaRegistroCajaCompensacion() {
		return fechaRegistroCajaCompensacion;
	}
	public void setFechaRegistroCajaCompensacion(String fechaRegistroCajaCompensacion) {
		this.fechaRegistroCajaCompensacion = fechaRegistroCajaCompensacion;
	}
	public String getFechaActualizacionCajaCompensacion() {
		return fechaActualizacionCajaCompensacion;
	}
	public void setFechaActualizacionCajaCompensacion(String fechaActualizacionCajaCompensacion) {
		this.fechaActualizacionCajaCompensacion = fechaActualizacionCajaCompensacion;
	}
	public PersonaAutor getRegistradoPorCajaCompensacion() {
		return registradoPorCajaCompensacion;
	}
	public void setRegistradoPorCajaCompensacion(PersonaAutor registradoPorCajaCompensacion) {
		this.registradoPorCajaCompensacion = registradoPorCajaCompensacion;
	}
	public PersonaAutor getActualizadoPorCajaCompensacion() {
		return actualizadoPorCajaCompensacion;
	}
	public void setActualizadoPorCajaCompensacion(PersonaAutor actualizadoPorCajaCompensacion) {
		this.actualizadoPorCajaCompensacion = actualizadoPorCajaCompensacion;
	}
    
    
}
