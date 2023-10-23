package com.itefs.trexsas.modelo;

import javax.persistence.Basic;
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
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "soat")
@JsonIgnoreProperties(value={ "vehiculo" }, allowSetters= true)
public class Soat {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_soat")
    private Integer idSoat;
	
	@Column(name = "fecha_inicio_soat")
    private String fechaInicioSoat;
	
    @Column(name = "fecha_vencimiento_soat")
    private String fechaVencimientoSoat;
   
    @Size(max = 16, message = "error, campo numeroSoat maximo de 16 digitos")
    @Column(name = "numero_soat")
    private String numeroSoat;
    @Column(name = "soat")
    private String soat;
    @JsonProperty("vehiculo")
    @JoinColumn(name = "vehiculo_id_vehiculo", referencedColumnName = "id_vehiculo")
    @OneToOne(optional = false)
    private Vehiculo vehiculo;

    @JoinColumn(name = "aseguradora_id_aseguradora", referencedColumnName = "id_aseguradora")
    @ManyToOne(optional = true)
    private Aseguradora aseguradora;
    
	public Soat() {
		super();
	}

	public Integer getIdSoat() {
		return idSoat;
	}

	public void setIdSoat(Integer idSoat) {
		this.idSoat = idSoat;
	}

	public String getFechaVencimientoSoat() {
		return fechaVencimientoSoat;
	}

	public void setFechaVencimientoSoat(String fechaVencimientoSoat) {
		this.fechaVencimientoSoat = fechaVencimientoSoat;
	}

	public String getNumeroSoat() {
		return numeroSoat;
	}

	public void setNumeroSoat(String numeroSoat) {
		this.numeroSoat = numeroSoat;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public Aseguradora getAseguradora() {
		return aseguradora;
	}

	public void setAseguradora(Aseguradora aseguradora) {
		this.aseguradora = aseguradora;
	}

	public String getFechaInicioSoat() {
		return fechaInicioSoat;
	}

	public void setFechaInicioSoat(String fechaInicioSoat) {
		this.fechaInicioSoat = fechaInicioSoat;
	}

	public String getSoat() {
		return soat;
	}

	public void setSoat(String soat) {
		this.soat = soat;
	}
    
}
