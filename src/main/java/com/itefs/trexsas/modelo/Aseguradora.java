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
@Table(name = "aseguradora")
public class Aseguradora {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_aseguradora")
    private Integer idAseguradora;
	@NotNull(message = "error, campo nombreAseguradora obligatorio")
    @Column(name = "nombre_aseguradora")
    private String nombreAseguradora;
	@NotNull(message = "error, campo estadoAseguradora obligatorio")
    @Column(name = "estado_aseguradora")
    private Integer estadoAseguradora;
    @Column(name = "fecha_registro_aseguradora")
	private String fechaRegistroAseguradora;
	@Column(name = "fecha_actualizacion_aseguradora")
	private String fechaActualizacionAseguradora;
	@ManyToOne
    @JoinColumn(name = "registrado_por_aseguradora", referencedColumnName = "id_persona")
	private PersonaAutor registradoPorAseguradora;
	@ManyToOne
    @JoinColumn(name = "actualizado_por_aseguradora", referencedColumnName = "id_persona")
	private PersonaAutor actualizadoPorAseguradora;
    
    public Aseguradora() {
    	super();
    }

	public Integer getIdAseguradora() {
		return idAseguradora;
	}

	public void setIdAseguradora(Integer idAseguradora) {
		this.idAseguradora = idAseguradora;
	}

	public String getNombreAseguradora() {
		return nombreAseguradora;
	}

	public void setNombreAseguradora(String nombreAseguradora) {
		this.nombreAseguradora = nombreAseguradora;
	}

	public Integer getEstadoAseguradora() {
		return estadoAseguradora;
	}

	public void setEstadoAseguradora(Integer estadoAseguradora) {
		this.estadoAseguradora = estadoAseguradora;
	}

	public String getFechaRegistroAseguradora() {
		return fechaRegistroAseguradora;
	}

	public void setFechaRegistroAseguradora(String fechaRegistroAseguradora) {
		this.fechaRegistroAseguradora = fechaRegistroAseguradora;
	}

	public String getFechaActualizacionAseguradora() {
		return fechaActualizacionAseguradora;
	}

	public void setFechaActualizacionAseguradora(String fechaActualizacionAseguradora) {
		this.fechaActualizacionAseguradora = fechaActualizacionAseguradora;
	}

	public PersonaAutor getRegistradoPorAseguradora() {
		return registradoPorAseguradora;
	}

	public void setRegistradoPorAseguradora(PersonaAutor registradoPorAseguradora) {
		this.registradoPorAseguradora = registradoPorAseguradora;
	}

	public PersonaAutor getActualizadoPorAseguradora() {
		return actualizadoPorAseguradora;
	}

	public void setActualizadoPorAseguradora(PersonaAutor actualizadoPorAseguradora) {
		this.actualizadoPorAseguradora = actualizadoPorAseguradora;
	}
    
    
}
