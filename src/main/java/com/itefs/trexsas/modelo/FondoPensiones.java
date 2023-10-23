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
@Table(name = "fondo_pensiones")
public class FondoPensiones {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_fondo_pensiones")
    private Integer idFondoPensiones;
    @Size(max = 50, message = "error, campo fondoPensiones maximo de 50 digitos")
    @Column(name = "fondo_pensiones")
    private String fondoPensiones;
    @Column(name = "estado_fondo_pensiones")
    private Integer estadoFondoPensiones;
    @Column(name = "fecha_registro_fondo_pensiones")
	private String fechaRegistroFondoPensiones;
	@Column(name = "fecha_actualizacion_fondo_pensiones")
	private String fechaActualizacionFondoPensiones;
	@ManyToOne
    @JoinColumn(name = "registrado_por_fondo_pensiones", referencedColumnName = "id_persona")
	private PersonaAutor registradoPorFondoPensiones;
	@ManyToOne
    @JoinColumn(name = "actualizado_por_fondo_pensiones", referencedColumnName = "id_persona")
	private PersonaAutor actualizadoPorFondoPensiones;
    
	public FondoPensiones() {
		super();
	}

	public Integer getIdFondoPensiones() {
		return idFondoPensiones;
	}

	public void setIdFondoPensiones(Integer idFondoPensiones) {
		this.idFondoPensiones = idFondoPensiones;
	}

	public String getFondoPensiones() {
		return fondoPensiones;
	}

	public void setFondoPensiones(String fondoPensiones) {
		this.fondoPensiones = fondoPensiones;
	}

	public Integer getEstadoFondoPensiones() {
		return estadoFondoPensiones;
	}

	public void setEstadoFondoPensiones(Integer estadoFondoPensiones) {
		this.estadoFondoPensiones = estadoFondoPensiones;
	}

	public String getFechaRegistroFondoPensiones() {
		return fechaRegistroFondoPensiones;
	}

	public void setFechaRegistroFondoPensiones(String fechaRegistroFondoPensiones) {
		this.fechaRegistroFondoPensiones = fechaRegistroFondoPensiones;
	}

	public String getFechaActualizacionFondoPensiones() {
		return fechaActualizacionFondoPensiones;
	}

	public void setFechaActualizacionFondoPensiones(String fechaActualizacionFondoPensiones) {
		this.fechaActualizacionFondoPensiones = fechaActualizacionFondoPensiones;
	}

	public PersonaAutor getRegistradoPorFondoPensiones() {
		return registradoPorFondoPensiones;
	}

	public void setRegistradoPorFondoPensiones(PersonaAutor registradoPorFondoPensiones) {
		this.registradoPorFondoPensiones = registradoPorFondoPensiones;
	}

	public PersonaAutor getActualizadoPorFondoPensiones() {
		return actualizadoPorFondoPensiones;
	}

	public void setActualizadoPorFondoPensiones(PersonaAutor actualizadoPorFondoPensiones) {
		this.actualizadoPorFondoPensiones = actualizadoPorFondoPensiones;
	}
    
	
}
