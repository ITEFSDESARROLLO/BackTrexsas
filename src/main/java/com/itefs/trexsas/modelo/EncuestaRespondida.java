package com.itefs.trexsas.modelo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="encuesta_respondida")
public class EncuestaRespondida 
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_encuesta_respondida")
	private Long id;
	
	@NotNull
	@Column(name="id_conductor")
	private Integer conductor;
	
	@NotNull
	@Column(name="id_servicio")
	private Long servicio;
	
	@NotNull
	@Column(name="id_encuesta")
	private Long encuesta;
	
	@NotNull
	@Column(name="fecha_respuesta")
	private String fechaRespuesta;
	
	@OneToMany(mappedBy = "encuestaRespondida")
	private List<RespuestaEncuesta> respuestaEncuesta;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getConductor() {
		return conductor;
	}

	public void setConductor(Integer conductor) {
		this.conductor = conductor;
	}

	public Long getServicio() {
		return servicio;
	}

	public void setServicio(Long servicio) {
		this.servicio = servicio;
	}

	public Long getEncuesta() {
		return encuesta;
	}

	public void setEncuesta(Long encuesta) {
		this.encuesta = encuesta;
	}

	public List<RespuestaEncuesta> getRespuestaEncuesta() {
		return respuestaEncuesta;
	}

	public void setRespuestaEncuesta(List<RespuestaEncuesta> respuestaEncuesta) {
		this.respuestaEncuesta = respuestaEncuesta;
	}

	public String getFechaRespuesta() {
		return fechaRespuesta;
	}

	public void setFechaRespuesta(String fechaRespuesta) {
		this.fechaRespuesta = fechaRespuesta;
	}

	@Override
	public String toString() {
		return "EncuestaRespondida [id=" + id + ", conductor=" + conductor + ", servicio=" + servicio + ", encuesta="
				+ encuesta + ", respuestaEncuesta=" + respuestaEncuesta + "]";
	}
	
	
}
