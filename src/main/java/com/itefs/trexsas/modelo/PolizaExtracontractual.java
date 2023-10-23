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
@Table(name = "poliza_extracontractual")
@JsonIgnoreProperties(value={ "vehiculo" }, allowSetters= true)
public class PolizaExtracontractual {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_poliza_extracontractual")
    private Integer idPolizaExtracontractual;
	
	@Column(name = "fecha_inicio_poliza_extracontractual")
    private String fechaInicioPolizaExtracontractual;
	
	@Column(name = "fecha_vencimiento_poliza_extracontractual")
    private String fechaVencimientoPolizaExtracontractual;
	
	@Size(max = 16, message = "error, campo numeroPolizaExtracontractual maximo de 16 digitos")
    @Column(name = "numero_poliza_extracontractual")
    private String numeroPolizaExtracontractual;
	@Column(name = "poliza_extracontractual")
    private String polizaExtracontractual;
    @JsonProperty("vehiculo")
    @JoinColumn(name = "vehiculo_id_vehiculo", referencedColumnName = "id_vehiculo")
    @OneToOne(optional = false)
    private Vehiculo vehiculo;
    
    @JoinColumn(name = "aseguradora_id_aseguradora", referencedColumnName = "id_aseguradora")
    @ManyToOne(optional = true)
    private Aseguradora aseguradora;
    
	public PolizaExtracontractual() {
		super();
	}

	public Integer getIdPolizaExtracontractual() {
		return idPolizaExtracontractual;
	}

	public void setIdPolizaExtracontractual(Integer idPolizaExtracontractual) {
		this.idPolizaExtracontractual = idPolizaExtracontractual;
	}

	public String getFechaVencimientoPolizaExtracontractual() {
		return fechaVencimientoPolizaExtracontractual;
	}

	public void setFechaVencimientoPolizaExtracontractual(String fechaVencimientoPolizaExtracontractual) {
		this.fechaVencimientoPolizaExtracontractual = fechaVencimientoPolizaExtracontractual;
	}

	public String getNumeroPolizaExtracontractual() {
		return numeroPolizaExtracontractual;
	}

	public void setNumeroPolizaExtracontractual(String numeroPolizaExtracontractual) {
		this.numeroPolizaExtracontractual = numeroPolizaExtracontractual;
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

	public String getFechaInicioPolizaExtracontractual() {
		return fechaInicioPolizaExtracontractual;
	}

	public void setFechaInicioPolizaExtracontractual(String fechaInicioPolizaExtracontractual) {
		this.fechaInicioPolizaExtracontractual = fechaInicioPolizaExtracontractual;
	}

	public String getPolizaExtracontractual() {
		return polizaExtracontractual;
	}

	public void setPolizaExtracontractual(String polizaExtracontractual) {
		this.polizaExtracontractual = polizaExtracontractual;
	}
    
    
}
