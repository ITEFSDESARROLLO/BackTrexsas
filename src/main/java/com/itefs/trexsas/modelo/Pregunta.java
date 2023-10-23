package com.itefs.trexsas.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="pregunta_encuesta")
public class Pregunta 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_pregunta")
	private Long id;
	@NotNull
	@Column(name="descripcion_pregunta")
	private String descripcion;
	
	//@JsonIgnore
	//@ManyToOne
    //@JoinColumn(name="encuesta")
	@NotNull
	@JsonIgnore
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name="encuesta")
	private Encuesta encuesta;
	
	//private Long encuesta;
	
	//@OneToMany(cascade = CascadeType.ALL)
	//@JoinColumn(name="pregunta")
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy="pregunta",orphanRemoval = true)
	private List<Opcion> opcionesPregunta;

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

	public Encuesta getEncuesta() {
		return encuesta;
	}

	public void setEncuesta(Encuesta encuesta) {
		this.encuesta = encuesta;
	}

	/*public Long getEncuesta() {
	return encuesta;
	}

	public void setEncuesta(Long encuesta) {
	this.encuesta = encuesta;
	}*/

	public List<Opcion> getOpcionesPregunta() {
		return opcionesPregunta;
	}

	public void setOpcionesPregunta(List<Opcion> opcionesPregunta) {
		this.opcionesPregunta = opcionesPregunta;
	}
	
	public void eliminarOpcion(Opcion eliminado)
	{
		
				this.opcionesPregunta.remove(eliminado);
				
		
		
		
	}
	
	public void agregarOpcion(Opcion opcion)
	{
		if(this.opcionesPregunta!=null)
		{
			this.opcionesPregunta.add(opcion);
		}else
		{
			this.opcionesPregunta = new ArrayList<Opcion>();
			this.opcionesPregunta.add(opcion);
		}
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "id : "+this.id;
	}
	
	
	
}
