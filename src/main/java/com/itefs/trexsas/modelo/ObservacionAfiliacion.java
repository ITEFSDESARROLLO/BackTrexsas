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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "observacion_afiliacion")
@JsonIgnoreProperties(value={ "afiliacion" }, allowSetters= true)
public class ObservacionAfiliacion {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_observacion_afiliacion")
    private Long idObservacionAfiliacion;
	@NotNull(message = "error, campo obligatorio")
    @Column(name = "observacion_afiliacion")
    private String observacionAfiliacion;
	@Column(name = "fecha_registro_observacion")
	private String fechaRegistroObservacion;
	@ManyToOne
    @JoinColumn(name = "registrado_por_observacion", referencedColumnName = "id_persona")
	private PersonaAutor registradoPorObservacion;
	@ManyToOne
	@JsonProperty("afiliacion")
    @JoinColumn(name = "afiliacion_id_afiliacion", updatable = false)
    private Afiliacion afiliacion;
	
	public ObservacionAfiliacion() {
		super();
	}

	public Long getIdObservacionAfiliacion() {
		return idObservacionAfiliacion;
	}

	public void setIdObservacionAfiliacion(Long idObservacionAfiliacion) {
		this.idObservacionAfiliacion = idObservacionAfiliacion;
	}

	public String getObservacionAfiliacion() {
		return observacionAfiliacion;
	}

	public void setObservacionAfiliacion(String observacionAfiliacion) {
		this.observacionAfiliacion = observacionAfiliacion;
	}
	
	
	public Afiliacion getAfiliacion() {
		return afiliacion;
	}

	public void setAfiliacion(Afiliacion afiliacion) {
		this.afiliacion = afiliacion;
	}

	public String getFechaRegistroObservacion() {
		return fechaRegistroObservacion;
	}

	public void setFechaRegistroObservacion(String fechaRegistroObservacion) {
		this.fechaRegistroObservacion = fechaRegistroObservacion;
	}

	public PersonaAutor getRegistradoPorObservacion() {
		return registradoPorObservacion;
	}

	public void setRegistradoPorObservacion(PersonaAutor registradoPorObservacion) {
		this.registradoPorObservacion = registradoPorObservacion;
	}
	
	
}
