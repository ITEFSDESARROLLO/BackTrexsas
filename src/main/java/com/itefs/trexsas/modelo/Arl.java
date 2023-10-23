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
@Table(name = "arl")
public class Arl {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_arl")
    private Integer idArl;
    @Size(max = 50, message = "error, campo arl maximo de 50 digitos")
    @Column(name = "arl")
    private String arl;
    @Column(name = "estado_arl")
    private Integer estadoArl;
    @Column(name = "fecha_registro_arl")
	private String fechaRegistroArl;
	@Column(name = "fecha_actualizacion_arl")
	private String fechaActualizacionArl;
	@ManyToOne
    @JoinColumn(name = "registrado_por_arl", referencedColumnName = "id_persona")
	private PersonaAutor registradoPorArl;
	@ManyToOne
    @JoinColumn(name = "actualizado_por_arl", referencedColumnName = "id_persona")
	private PersonaAutor actualizadoPorArl;
    
	public Arl() {
		super();
	}
	
	public Integer getIdArl() {
		return idArl;
	}
	public void setIdArl(Integer idArl) {
		this.idArl = idArl;
	}
	public String getArl() {
		return arl;
	}
	public void setArl(String arl) {
		this.arl = arl;
	}
	public Integer getEstadoArl() {
		return estadoArl;
	}
	public void setEstadoArl(Integer estadoArl) {
		this.estadoArl = estadoArl;
	}

	public String getFechaRegistroArl() {
		return fechaRegistroArl;
	}

	public void setFechaRegistroArl(String fechaRegistroArl) {
		this.fechaRegistroArl = fechaRegistroArl;
	}

	public String getFechaActualizacionArl() {
		return fechaActualizacionArl;
	}

	public void setFechaActualizacionArl(String fechaActualizacionArl) {
		this.fechaActualizacionArl = fechaActualizacionArl;
	}

	public PersonaAutor getRegistradoPorArl() {
		return registradoPorArl;
	}

	public void setRegistradoPorArl(PersonaAutor registradoPorArl) {
		this.registradoPorArl = registradoPorArl;
	}

	public PersonaAutor getActualizadoPorArl() {
		return actualizadoPorArl;
	}

	public void setActualizadoPorArl(PersonaAutor actualizadoPorArl) {
		this.actualizadoPorArl = actualizadoPorArl;
	}
    
    
}
