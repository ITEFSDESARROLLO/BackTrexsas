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
@Table(name = "clase")
public class Clase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Basic(optional = false)
    @Column(name = "id_clase")
    private Integer idClase;
    @NotNull(message = "error, campo clase obligatorio")
    @Size(max = 30, message = "error, campo clase maximo de 30 digitos")
    @Column(name = "clase")
    private String clase;
    @NotNull(message = "error, campo estadoClase obligatorio")
    @Column(name = "estado_clase")
    private Integer estadoClase;
    @Column(name = "fecha_registro_clase")
	private String fechaRegistroClase;
	@Column(name = "fecha_actualizacion_clase")
	private String fechaActualizacionClase;
	@ManyToOne
    @JoinColumn(name = "registrado_por_clase", referencedColumnName = "id_persona")
	private PersonaAutor registradoPorClase;
	@ManyToOne
    @JoinColumn(name = "actualizado_por_clase", referencedColumnName = "id_persona")
	private PersonaAutor actualizadoPorClase;
    
    public Clase() {
    	super();
    }

	public Integer getIdClase() {
		return idClase;
	}

	public void setIdClase(Integer idClase) {
		this.idClase = idClase;
	}

	public String getClase() {
		return clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	public Integer getEstadoClase() {
		return estadoClase;
	}

	public void setEstadoClase(Integer estadoClase) {
		this.estadoClase = estadoClase;
	}

	public String getFechaRegistroClase() {
		return fechaRegistroClase;
	}

	public void setFechaRegistroClase(String fechaRegistroClase) {
		this.fechaRegistroClase = fechaRegistroClase;
	}

	public String getFechaActualizacionClase() {
		return fechaActualizacionClase;
	}

	public void setFechaActualizacionClase(String fechaActualizacionClase) {
		this.fechaActualizacionClase = fechaActualizacionClase;
	}

	public PersonaAutor getRegistradoPorClase() {
		return registradoPorClase;
	}

	public void setRegistradoPorClase(PersonaAutor registradoPorClase) {
		this.registradoPorClase = registradoPorClase;
	}

	public PersonaAutor getActualizadoPorClase() {
		return actualizadoPorClase;
	}

	public void setActualizadoPorClase(PersonaAutor actualizadoPorClase) {
		this.actualizadoPorClase = actualizadoPorClase;
	}
	
	
    
}
