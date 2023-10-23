package com.itefs.trexsas.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="capacitacion")
public class Capacitacion 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_capacitacion")
	private Long id;
	@NotNull
	@Column(name="nombre_capacitacion")
    private String nombre;
	@NotNull
	@Column(name="descripcion")
    private String descripcion;
	@NotNull
	@Column(name="fecha_publicacion")
    private String fechaPublicacion;
	@Column(name="fecha_inicio_inscripcion")
    private String fechaInscripcion;
	@Column(name="fecha_fin_inscripcion")
    private String fechaFinInscripcion;
	@Column(name="fecha_inicio")
    private String fechaInicioCapacitacion;
    @Column(name="fecha_fin")
    private String fechaFinCapacitacion;
    @NotNull
    @Column(name="estado")
    private long estado;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getFechaPublicacion() {
		return fechaPublicacion;
	}
	public void setFechaPublicacion(String fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}
	public String getFechaInscripcion() {
		return fechaInscripcion;
	}
	public void setFechaInscripcion(String fechaInscripcion) {
		this.fechaInscripcion = fechaInscripcion;
	}
	public String getFechaFinInscripcion() {
		return fechaFinInscripcion;
	}
	public void setFechaFinInscripcion(String fechaFinInscripcion) {
		this.fechaFinInscripcion = fechaFinInscripcion;
	}
	public String getFechaInicioCapacitacion() {
		return fechaInicioCapacitacion;
	}
	public void setFechaInicioCapacitacion(String fechaInicioCapacitacion) {
		this.fechaInicioCapacitacion = fechaInicioCapacitacion;
	}
	public String getFechaFinCapacitacion() {
		return fechaFinCapacitacion;
	}
	public void setFechaFinCapacitacion(String fechaFinCapacitacion) {
		this.fechaFinCapacitacion = fechaFinCapacitacion;
	}
	public long getEstado() {
		return estado;
	}
	public void setEstado(long estado) {
		this.estado = estado;
	}
}
