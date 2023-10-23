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
@Table(name = "eps")
public class Eps {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_eps")
    private Integer idEps;
	@Size(max = 50, message = "error, campo eps maximo de 50 digitos")
    @Column(name = "eps")
    private String eps;
    @Column(name = "estado_eps")
    private Integer estadoEps;
    @Column(name = "fecha_registro_eps")
	private String fechaRegistroEps;
	@Column(name = "fecha_actualizacion_eps")
	private String fechaActualizacionEps;
	@ManyToOne
    @JoinColumn(name = "registrado_por_eps", referencedColumnName = "id_persona")
	private PersonaAutor registradoPorEps;
	@ManyToOne
    @JoinColumn(name = "actualizado_por_eps", referencedColumnName = "id_persona")
	private PersonaAutor actualizadoPorEps;
    
	public Eps() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getIdEps() {
		return idEps;
	}

	public void setIdEps(Integer idEps) {
		this.idEps = idEps;
	}

	public String getEps() {
		return eps;
	}

	public void setEps(String eps) {
		this.eps = eps;
	}

	public Integer getEstadoEps() {
		return estadoEps;
	}

	public void setEstadoEps(Integer estadoEps) {
		this.estadoEps = estadoEps;
	}

	public String getFechaRegistroEps() {
		return fechaRegistroEps;
	}

	public void setFechaRegistroEps(String fechaRegistroEps) {
		this.fechaRegistroEps = fechaRegistroEps;
	}

	public String getFechaActualizacionEps() {
		return fechaActualizacionEps;
	}

	public void setFechaActualizacionEps(String fechaActualizacionEps) {
		this.fechaActualizacionEps = fechaActualizacionEps;
	}

	public PersonaAutor getRegistradoPorEps() {
		return registradoPorEps;
	}

	public void setRegistradoPorEps(PersonaAutor registradoPorEps) {
		this.registradoPorEps = registradoPorEps;
	}

	public PersonaAutor getActualizadoPorEps() {
		return actualizadoPorEps;
	}

	public void setActualizadoPorEps(PersonaAutor actualizadoPorEps) {
		this.actualizadoPorEps = actualizadoPorEps;
	}
    
	
    
}
