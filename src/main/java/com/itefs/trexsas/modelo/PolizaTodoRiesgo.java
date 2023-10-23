package com.itefs.trexsas.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="poliza_todo_riesgo")
public class PolizaTodoRiesgo
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_poliza_todo_riesgo")
	private Long id;
	
	
	@Column(name="numero_poliza")
	private String numeroPoliza;
	
	
	@OneToOne
	@JoinColumn(name = "id_aseguradora",referencedColumnName = "id_aseguradora")
	private Aseguradora aseguradora;
	
	
	@Column(name="fecha_inicio")
	private String fechaInicio;
	
	
	@Column(name="fecha_fin")
	private String fechaFin;
	
	
	@Column(name="nombre_archivo_poliza")
	private String nombreArchivoPoliza;
	
	@JsonIgnore
	@OneToOne
    @JoinColumn(name = "id_vehiculo", referencedColumnName = "id_vehiculo")
    private Vehiculo vehiculo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroPoliza() {
		return numeroPoliza;
	}

	public void setNumeroPoliza(String numeroPoliza) {
		this.numeroPoliza = numeroPoliza;
	}

	public Aseguradora getAseguradora() {
		return aseguradora;
	}

	public void setAseguradora(Aseguradora aseguradora) {
		this.aseguradora = aseguradora;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getNombreArchivoPoliza() {
		return nombreArchivoPoliza;
	}

	public void setNombreArchivoPoliza(String nombreArchivoPoliza) {
		this.nombreArchivoPoliza = nombreArchivoPoliza;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}
	
	

}
