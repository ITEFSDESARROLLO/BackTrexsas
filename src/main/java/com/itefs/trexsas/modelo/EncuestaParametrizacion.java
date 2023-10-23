package com.itefs.trexsas.modelo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="encuesta_parametro")
public class EncuestaParametrizacion 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_encuesta_parametro")
	private Long id;
	
	@OneToOne(cascade=CascadeType.ALL)//one-to-one
    @JoinColumn(name="id_encuesta")
	private Encuesta encuesta;
	
	@NotNull
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="tipo_encuesta")
	private Long tipoEncuesta;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Encuesta getEncuesta() {
		return encuesta;
	}

	public void setEncuesta(Encuesta encuesta) {
		this.encuesta = encuesta;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getTipoEncuesta() {
		return tipoEncuesta;
	}

	public void setTipoEncuesta(Long tipoEncuesta) {
		this.tipoEncuesta = tipoEncuesta;
	}
}
