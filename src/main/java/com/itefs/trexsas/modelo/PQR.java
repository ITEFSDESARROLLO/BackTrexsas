package com.itefs.trexsas.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="peticiones_quejas_reclamos")
public class PQR 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_pqr")
	private Long id;
    @NotNull
    @Column(name="descripcion")
    private String descripcion;
    @NotNull
    @Column(name="tipo")
    private long tipo;
    @NotNull
    @Column(name="fecha_publicacion")
    private String fechaPublicacion;
    @NotNull
    @Column(name="redactor")
    private String redactor;
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
	public long getTipo() {
		return tipo;
	}
	public void setTipo(long tipo) {
		this.tipo = tipo;
	}
	public String getFechaPublicacion() {
		return fechaPublicacion;
	}
	public void setFechaPublicacion(String fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}
	public String getRedactor() {
		return redactor;
	}
	public void setRedactor(String redactor) {
		this.redactor = redactor;
	}
}
