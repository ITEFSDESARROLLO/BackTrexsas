package com.itefs.trexsas.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="opcion_pregunta")
public class Opcion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_opcion")
	private Long id;
	@NotNull
	@Column(name="descripcion_opcion")
	private String descripcion;
	
	@JsonIgnore
	//@ManyToOne(fetch= FetchType.LAZY)
	@ManyToOne
    @JoinColumn(name="pregunta")
	private Pregunta pregunta;
	
	
	
	
	//private Long pregunta;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Pregunta getPregunta() {
		return pregunta;
	}

	public void setPregunta(Pregunta pregunta) {
		this.pregunta = pregunta;
	}
	
	/*public Long getPregunta()
	{
		return pregunta;
	}
	
	public void setPregunta(Long pregunta)
	{
		this.pregunta = pregunta;
	}*/

	

	
	@Override
	public String toString() {
		return "Opcion [id=" + id + ", descripcion=" + descripcion + ", pregunta=" + pregunta 
				+ "]";
	}


	
	
	
	
	
	

}
