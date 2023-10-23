package com.itefs.trexsas.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="respuesta_encuesta")
public class RespuestaEncuesta 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_respuesta_encuesta")
	private Long id;
	
	@NotNull
	@Column(name="id_pregunta")
	private Long pregunta;
	
	@NotNull
	@Column(name="id_opcion")
	private Long opcion;
	
	@JsonIgnore
	@ManyToOne
    @JoinColumn(name="id_encuesta_respondida")
	private EncuestaRespondida encuestaRespondida;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPregunta() {
		return pregunta;
	}

	public void setPregunta(Long pregunta) {
		this.pregunta = pregunta;
	}

	public Long getOpcion() {
		return opcion;
	}

	public void setOpcion(Long opcion) {
		this.opcion = opcion;
	}

	public EncuestaRespondida getEncuestaRespondida() {
		return encuestaRespondida;
	}

	public void setEncuestaRespondida(EncuestaRespondida encuestaRespondida) {
		this.encuestaRespondida = encuestaRespondida;
	}

}
