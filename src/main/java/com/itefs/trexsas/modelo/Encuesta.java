package com.itefs.trexsas.modelo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="encuesta")
public class Encuesta 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_encuesta")
	private Long id;
	
	@NotNull
	@Column(name="nombre_encuesta")
	private String nombreEncuesta;
	
	@NotNull
	@Column(name="fecha_publicacion")
	private String fechaPublicacion;
	
	@NotNull
	@Column(name="perfil_usuario")
	private Integer perfilUsuario;
	
	//@OneToMany(cascade = CascadeType.ALL)
	//@OneToMany(cascade = CascadeType.ALL)
	//@JoinColumn(name="encuesta")
	@OneToMany(cascade = CascadeType.ALL, mappedBy="encuesta",orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Pregunta> preguntasEncuesta;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreEncuesta() {
		return nombreEncuesta;
	}

	public void setNombreEncuesta(String nombreEncuesta) {
		this.nombreEncuesta = nombreEncuesta;
	}

	public String getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(String fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public Integer getPerfilUsuario() {
		return perfilUsuario;
	}

	public void setPerfilUsuario(Integer prefilUsuario) {
		this.perfilUsuario = prefilUsuario;
	}

	public List<Pregunta> getPreguntasEncuesta() {
		return preguntasEncuesta;
	}

	public void setPreguntasEncuesta(List<Pregunta> preguntasEncuesta) {
		this.preguntasEncuesta = preguntasEncuesta;
	}
	
	@Override
	public String toString() {
		return "id : "+this.id+" | nombre encuesta : "+this.nombreEncuesta+" | fecha : "+this.fechaPublicacion+" | perfil : "+this.perfilUsuario;
	}
	
	public void eliminarPregunta(Pregunta pregunta)
	{
		this.preguntasEncuesta.remove(pregunta);
	}
	
	public void agregarPregunta(Pregunta pregunta)
	{
		this.preguntasEncuesta.add(pregunta);
	}
	
	

}
